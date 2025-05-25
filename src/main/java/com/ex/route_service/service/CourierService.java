package com.ex.route_service.service;

import com.ex.route_service.client.OrderServiceClient;
import com.ex.route_service.dto.OrderServiceDto.OrderResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCourierResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCouriersForOrderResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.RouteEventStatusRequestDto;
import com.ex.route_service.entity.Courier;
import com.ex.route_service.entity.LocationPoint;
import com.ex.route_service.enums.CourierStatus;
import com.ex.route_service.enums.OrderStatus;
import com.ex.route_service.enums.RouteEventStatus;
import com.ex.route_service.mapper.CourierMapper;
import com.ex.route_service.mapper.LocationPointMapper;
import com.ex.route_service.mapper.RouteEventMapper;
import com.ex.route_service.repository.CourierJdbcRepository;
import com.ex.route_service.repository.CourierRepository;
import com.ex.route_service.repository.RouteEventRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;
    private final RouteEventRepository routeEventRepository;
    private final RouteEventMapper routeEventMapper;
    private final CourierMapper courierMapper;
    private final OpenRouteService openRouteService;
    private final CourierJdbcRepository courierJdbcRepository;
    private final OrderServiceClient orderServiceClient;
    private final LocationPointService locationPointService;
    private final RouteEventService routeEventService;
    private final LocationPointMapper locationPointMapper;

    public GetCourierResponseDto getCourier(UUID courierId) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));
        return courierMapper.toResponseDto(courier);
    }

    //    достает ближайших курьеров не учитывая дорог
    public List<GetCouriersForOrderResponseDto> getCouriersForOrder(
            double latitudeClient,
            double longitudeClient,
            double latitudeRestaurant,
            double longitudeRestaurant,
            UUID orderId
    ) {
        ////        надо их где то хранить, чтобы можно было изменять через ui
        double maxFootDistance = 2500;
        double maxBikeDistance = 5000;
        double maxCarDistance = 10000;

//        достаем сырые данные
        List<Map<String, Object>> rawRows = courierJdbcRepository.findNearbyCouriersRaw(
                latitudeClient,
                longitudeClient,
                latitudeRestaurant,
                longitudeRestaurant,
                maxFootDistance,
                maxBikeDistance,
                maxCarDistance
        );

        return courierMapper.toListResponseDto(rawRows, orderId);
    }

    //    кажется надо отдельный routeService создать, не курьер и не openRoute
    public String getRoute(Double longitudeCourier, Double latitudeCourier,
                           Double longitudeRestaurant, Double latitudeRestaurant,
                           Double longitudeClient, Double latitudeClient,
                           UUID courierId, UUID orderId) throws Exception {

        Courier courier = courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));

        OrderResponseDto orderDto = orderServiceClient.getOrder(orderId);
        String orderStatus = orderDto.getOrderStatus();

        OrderStatus status = OrderStatus.from(orderStatus)
                .orElseThrow(() -> new IllegalArgumentException("Unknown order status: " + orderStatus));

        String route;
        if (status == OrderStatus.CONFIRMED || status == OrderStatus.PREPARING || status == OrderStatus.READY_FOR_PICKUP) {
            route = openRouteService.getRoute(longitudeCourier, latitudeCourier,
                    longitudeRestaurant, latitudeRestaurant,
                    longitudeClient, latitudeClient, courier.getTransportType());
        } else {
            route = openRouteService.getRoute(longitudeCourier, latitudeCourier,
                    longitudeClient, latitudeClient, courier.getTransportType());
        }

        System.out.println(route);
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

        if (RouteEventStatus.TAKE_ORDER.equals(statusRequestDto.getRouteEventStatus())) {
            newCourierStatus = CourierStatus.BUSY;

        } else if (RouteEventStatus.ORDER_DELIVERED.equals(statusRequestDto.getRouteEventStatus())) {
            newCourierStatus = CourierStatus.READY;

        } else if (RouteEventStatus.SHIFT_STARTED.equals(statusRequestDto.getRouteEventStatus())) {
            if (!courier.getCourierStatus().equals(CourierStatus.FINISHED)) {
                throw new EntityNotFoundException("У курьера есть незавершенная работа: " + courierId);
            }
            newCourierStatus = CourierStatus.READY;

        } else if (RouteEventStatus.PAUSE_STARTED.equals(statusRequestDto.getRouteEventStatus())) {
            if (!courier.getCourierStatus().equals(CourierStatus.BUSY)) {
                throw new EntityNotFoundException("У курьера есть незавершенный заказ: " + courierId);
            }
            newCourierStatus = CourierStatus.PAUSED;

        } else if (RouteEventStatus.PAUSE_FINISHED.equals(statusRequestDto.getRouteEventStatus())) {
            newCourierStatus = CourierStatus.READY;

        } else if (RouteEventStatus.SHIFT_FINISHED.equals(statusRequestDto.getRouteEventStatus())) {
            if (!courier.getCourierStatus().equals(CourierStatus.BUSY)) {
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
        routeEventService.save(statusRequestDto.getRouteEventStatus(), statusRequestDto.getOrderId(), courier, locationPoint);
    }

}
