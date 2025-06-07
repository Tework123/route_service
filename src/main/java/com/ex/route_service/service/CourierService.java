package com.ex.route_service.service;

import com.ex.route_service.client.FinanceServiceClient;
import com.ex.route_service.client.OrderServiceClient;
import com.ex.route_service.dto.OpenRouteServiceDto.GetRouteResponseDto;
import com.ex.route_service.dto.OrderServiceDto.OrderResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCourierResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCouriersForOrderResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.RouteEventStatusRequestDto;
import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationResponseDto;
import com.ex.route_service.entity.Courier;
import com.ex.route_service.entity.LocationPoint;
import com.ex.route_service.enums.CourierStatus;
import com.ex.route_service.enums.OrderStatus;
import com.ex.route_service.enums.RouteEventStatus;
import com.ex.route_service.enums.WeatherStatus;
import com.ex.route_service.mapper.CourierMapper;
import com.ex.route_service.repository.CourierJdbcRepository;
import com.ex.route_service.repository.CourierRepository;
import com.ex.route_service.repository.RouteEventRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;
    private final RouteEventRepository routeEventRepository;
    private final CourierMapper courierMapper;
    private final OpenRouteService openRouteService;
    private final CourierJdbcRepository courierJdbcRepository;
    private final OrderServiceClient orderServiceClient;
    private final LocationPointService locationPointService;
    private final RouteEventService routeEventService;
    private final OpenWeatherMapService openWeatherMapService;
    private final FinanceServiceClient financeServiceClient;

    public GetCourierResponseDto getCourier(UUID courierId) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));
        return courierMapper.toResponseDto(courier);
    }

    //    достает ближайших курьеров не учитывая дорог
    public List<GetCouriersForOrderResponseDto> getCouriersForOrder(
            double longitudeClient,
            double latitudeClient,
            double longitudeRestaurant,
            double latitudeRestaurant,
            UUID orderId
    ) {
        ////        надо их где то хранить, чтобы можно было изменять через ui
        double maxFootDistance = 2500;
        double maxBikeDistance = 5000;
        double maxCarDistance = 10000;

//        пытаемся достать из кеша, если слишком старые(более 2 минут), то достаем из базы:


//        достаем сырые данные
        List<Map<String, Object>> rawRows = courierJdbcRepository.findNearbyCouriersRaw(
                longitudeClient, latitudeClient,
                longitudeRestaurant, latitudeRestaurant,
                maxFootDistance,
                maxBikeDistance,
                maxCarDistance
        );

        return courierMapper.toListResponseDto(rawRows, orderId);
    }

    public GetRouteResponseDto getRoute(
            Double longitudeRestaurant, Double latitudeRestaurant,
            Double longitudeClient, Double latitudeClient,
            UUID courierId, UUID orderId) throws Exception {

        Courier courier = courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));

        LocationResponseDto lastLocationPoint = locationPointService.getLastLocationPoint(courierId);

//      получаем статус заказа из сервиса заказов
        OrderResponseDto orderDto = orderServiceClient.getOrder(orderId);
        String orderStatus = orderDto.getOrderStatus();

        OrderStatus status = OrderStatus.from(orderStatus)
                .orElseThrow(() -> new IllegalArgumentException("Unknown order status: " + orderStatus));

        GetRouteResponseDto route;
        if (status == OrderStatus.CONFIRMED || status == OrderStatus.PREPARING || status == OrderStatus.READY_FOR_PICKUP) {
            route = openRouteService.getRoute(lastLocationPoint.getLongitude(),
                    lastLocationPoint.getLatitude(),
                    longitudeRestaurant, latitudeRestaurant,
                    longitudeClient, latitudeClient, courier.getTransportType());
        } else {
            route = openRouteService.getRoute(lastLocationPoint.getLongitude(),
                    lastLocationPoint.getLatitude(),
                    longitudeClient, latitudeClient, courier.getTransportType());
        }

        return route;

    }

    //        курьер берет заказ(хочет его доставить), в сервис заказов улетает запрос, добавляется поле id курьера к заказу
//        из сервиса заказов летит запрос сюда на изменение статусов:
//    или курьер совершает другое действие с заказом
    @Transactional
    public void changeCourierStatus(UUID courierId, RouteEventStatusRequestDto statusRequestDto) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));

        LocationPoint locationPoint = locationPointService.save(statusRequestDto.getLocationDto(), courierId);

        CourierStatus newCourierStatus = null;
        WeatherStatus weatherStatus = null;

        if (RouteEventStatus.TAKE_ORDER.equals(statusRequestDto.getRouteEventStatus())) {
            if (courier.getCourierStatus().equals(CourierStatus.BUSY)) {
                throw new EntityNotFoundException("У курьера есть незавершенная работа: " + courierId);
            }
            if (courier.getCourierStatus().equals(CourierStatus.PAUSED)) {
                throw new EntityNotFoundException("Курьер на паузе: " + courierId);
            }
            if (courier.getCourierStatus().equals(CourierStatus.FINISHED)) {
                throw new EntityNotFoundException("Курьер не работает " + courierId);
            }
            newCourierStatus = CourierStatus.BUSY;
            weatherStatus = openWeatherMapService.getWeather(statusRequestDto.getLocationDto());

        } else if (RouteEventStatus.ORDER_DELIVERED.equals(statusRequestDto.getRouteEventStatus())) {
            newCourierStatus = CourierStatus.READY;
            routeEventService.sendRouteEvents(statusRequestDto.getOrderId(), courierId);

        } else if (RouteEventStatus.SHIFT_STARTED.equals(statusRequestDto.getRouteEventStatus())) {
            if (courier.getCourierStatus().equals(CourierStatus.FINISHED)) {
                throw new EntityNotFoundException("У курьера есть незавершенная работа: " + courierId);
            }
            newCourierStatus = CourierStatus.READY;

        } else if (RouteEventStatus.PAUSE_STARTED.equals(statusRequestDto.getRouteEventStatus())) {
            if (courier.getCourierStatus().equals(CourierStatus.BUSY)) {
                throw new EntityNotFoundException("У курьера есть незавершенный заказ: " + courierId);
            }
            newCourierStatus = CourierStatus.PAUSED;

        } else if (RouteEventStatus.PAUSE_FINISHED.equals(statusRequestDto.getRouteEventStatus())) {
            if (courier.getCourierStatus().equals(CourierStatus.PAUSED)) {
                throw new EntityNotFoundException("Курьер не на паузе: " + courierId);
            }
            newCourierStatus = CourierStatus.READY;

        } else if (RouteEventStatus.SHIFT_FINISHED.equals(statusRequestDto.getRouteEventStatus())) {
            if (courier.getCourierStatus().equals(CourierStatus.BUSY)) {
                throw new EntityNotFoundException("У курьера есть незавершенный заказ: " + courierId);
            }
            newCourierStatus = CourierStatus.FINISHED;

        } else if (RouteEventStatus.ORDER_CANCELLED.equals(statusRequestDto.getRouteEventStatus())) {
            if (statusRequestDto.getOrderId() == null) {
                throw new EntityNotFoundException("У курьера нет прикрепленного заказа: " + courierId);
            }
            newCourierStatus = CourierStatus.PAUSED;
        }

        if (newCourierStatus != null) {
            courier.setCourierStatus(newCourierStatus);
            courierRepository.save(courier);
        }
        routeEventService.save(statusRequestDto.getRouteEventStatus(), statusRequestDto.getOrderId(), courier, locationPoint, weatherStatus);
    }

}
