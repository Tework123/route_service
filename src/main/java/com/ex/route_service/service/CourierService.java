package com.ex.route_service.service;

import com.ex.route_service.client.FinanceServiceClient;
import com.ex.route_service.client.OrderServiceClient;
import com.ex.route_service.dto.OpenRouteServiceDto.GetRouteResponseDto;
import com.ex.route_service.dto.OrderServiceDto.OrderResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.CreateCourierRequestDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCourierResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCouriersForOrderResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.RouteEventStatusRequestDto;
import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Сервис для управления курьерами и их взаимодействиями с заказами, маршрутами и локациями.
 */
@Slf4j
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
    private final RedisService redisService;

    private static final double MAX_FOOT_DISTANCE = 2500;
    private static final double MAX_BIKE_DISTANCE = 5000;
    private static final double MAX_CAR_DISTANCE = 10000;

    /**
     * Получает информацию о курьере и его последней геолокации.
     *
     * @param courierId идентификатор курьера
     * @return DTO с информацией о курьере
     */
    public GetCourierResponseDto getCourier(UUID courierId) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));

        LocationDto locationDto = locationPointService.getLastLocationPoint(courierId);

        return courierMapper.toResponseDto(courier, locationDto);
    }

    /**
     * Создает нового курьера и сохраняет его текущую геолокацию.
     *
     * @param courierRequestDto данные для создания курьера
     */
    @Transactional
    public void createCourier(CreateCourierRequestDto courierRequestDto) {
        Courier courier = courierRepository.save(buildEntity(courierRequestDto));
        log.info("Курьер создан: courierId={}", courier.getCourierId());
        locationPointService.save(courierRequestDto.getCurrentLocation(), courier.getCourierId());
    }

    private Courier buildEntity(CreateCourierRequestDto courierRequestDto) {
        Courier courier = new Courier();
        courier.setCourierStatus(CourierStatus.FINISHED);
        courier.setTransportType(courierRequestDto.getTransportType());
        return courier;
    }

    /**
     * Находит ближайших курьеров к клиенту и ресторану, не учитывая дороги.
     *
     * @param longitudeClient     долгота клиента
     * @param latitudeClient      широта клиента
     * @param longitudeRestaurant долгота ресторана
     * @param latitudeRestaurant  широта ресторана
     * @param orderId             идентификатор заказа
     * @return список DTO с подходящими курьерами
     */
    public List<GetCouriersForOrderResponseDto> getCouriersForOrder(
            double longitudeClient,
            double latitudeClient,
            double longitudeRestaurant,
            double latitudeRestaurant,
            UUID orderId
    ) {
        List<Map<String, Object>> rawRows = courierJdbcRepository.findNearbyCouriersRaw(
                longitudeClient, latitudeClient,
                longitudeRestaurant, latitudeRestaurant,
                MAX_FOOT_DISTANCE,
                MAX_BIKE_DISTANCE,
                MAX_CAR_DISTANCE
        );
        return courierMapper.toListResponseDto(rawRows, orderId);
    }

    /**
     * Строит маршрут курьера в зависимости от статуса заказа.
     *
     * @param longitudeRestaurant долгота ресторана
     * @param latitudeRestaurant  широта ресторана
     * @param longitudeClient     долгота клиента
     * @param latitudeClient      широта клиента
     * @param courierId           идентификатор курьера
     * @param orderId             идентификатор заказа
     * @return маршрут между точками
     * @throws Exception если произошла ошибка получения маршрута
     */
    public GetRouteResponseDto getRoute(
            Double longitudeRestaurant, Double latitudeRestaurant,
            Double longitudeClient, Double latitudeClient,
            UUID courierId, UUID orderId) throws Exception {

        Courier courier = courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));

        LocationDto lastLocationPoint = locationPointService.getLastLocationPoint(courierId);

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

    /**
     * Обрабатывает смену статуса курьера в зависимости от действия.
     *
     * @param courierId        идентификатор курьера
     * @param statusRequestDto информация о новом статусе и местоположении
     */
    @Transactional
    public void changeCourierStatus(UUID courierId, RouteEventStatusRequestDto statusRequestDto) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(
                () -> new EntityNotFoundException("Курьер не найден: " + courierId));
        LocationPoint locationPoint = locationPointService.save(statusRequestDto.getLocationDto(), courierId);

        routeEventService.sendRouteEvents(statusRequestDto.getOrderId(), courierId);

        RouteEventStatus status = statusRequestDto.getRouteEventStatus();
        CourierStatus newCourierStatus = null;
        WeatherStatus weatherStatus = null;

        switch (status) {
            case TAKE_ORDER -> {
                validateTakeOrder(courier, courierId);
                newCourierStatus = CourierStatus.BUSY;
                weatherStatus = openWeatherMapService.getWeather(statusRequestDto.getLocationDto());
            }
            case ORDER_DELIVERED -> {
                newCourierStatus = CourierStatus.READY;
                routeEventService.sendRouteEvents(statusRequestDto.getOrderId(), courierId);
            }
            case SHIFT_STARTED -> {
                validateShiftStarted(courier, courierId);
                newCourierStatus = CourierStatus.READY;
            }
            case PAUSE_STARTED -> {
                validatePauseStarted(courier, courierId);
                newCourierStatus = CourierStatus.PAUSED;
            }
            case PAUSE_FINISHED -> {
                validatePauseFinished(courier, courierId);
                newCourierStatus = CourierStatus.READY;
            }
            case SHIFT_FINISHED -> {
                validateShiftFinished(courier, courierId);
                newCourierStatus = CourierStatus.FINISHED;
            }
            case ORDER_CANCELLED -> {
                validateOrderCancelled(statusRequestDto, courierId);
                newCourierStatus = CourierStatus.PAUSED;
            }
        }
        if (newCourierStatus != null) {
            courier.setCourierStatus(newCourierStatus);
            courierRepository.save(courier);
            log.info("Статус курьера {} изменен на {}", courierId, newCourierStatus);
        }
        routeEventService.save(status, statusRequestDto.getOrderId(), courier, locationPoint, weatherStatus);
    }

    private void validateTakeOrder(Courier courier, UUID courierId) {
        if (courier.getCourierStatus() == CourierStatus.BUSY) {
            throw new EntityNotFoundException("У курьера есть незавершенная работа: " + courierId);
        }
        if (courier.getCourierStatus() == CourierStatus.PAUSED) {
            throw new EntityNotFoundException("Курьер на паузе: " + courierId);
        }
        if (courier.getCourierStatus() == CourierStatus.FINISHED) {
            throw new EntityNotFoundException("Курьер не работает: " + courierId);
        }
    }

    private void validateShiftStarted(Courier courier, UUID courierId) {
        if (courier.getCourierStatus() == CourierStatus.FINISHED) {
            throw new EntityNotFoundException("У курьера есть незавершенная работа: " + courierId);
        }
    }

    private void validatePauseStarted(Courier courier, UUID courierId) {
        if (courier.getCourierStatus() == CourierStatus.BUSY) {
            throw new EntityNotFoundException("У курьера есть незавершенный заказ: " + courierId);
        }
    }

    private void validatePauseFinished(Courier courier, UUID courierId) {
        if (courier.getCourierStatus() != CourierStatus.PAUSED) {
            throw new EntityNotFoundException("Курьер не на паузе: " + courierId);
        }
    }

    private void validateShiftFinished(Courier courier, UUID courierId) {
        if (courier.getCourierStatus() == CourierStatus.BUSY) {
            throw new EntityNotFoundException("У курьера есть незавершенный заказ: " + courierId);
        }
    }

    private void validateOrderCancelled(RouteEventStatusRequestDto requestDto, UUID courierId) {
        if (requestDto.getOrderId() == null) {
            throw new EntityNotFoundException("У курьера нет прикрепленного заказа: " + courierId);
        }
    }

}
