package com.ex.route_service.service;

import com.ex.route_service.client.OrderServiceClient;
import com.ex.route_service.dto.OrderServiceDto.OrderResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCourierResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCouriersForOrderResponseDto;
import com.ex.route_service.dto.RouteServiceDto.routeEventDto.StartWorkRequestDto;
import com.ex.route_service.entity.Courier;
import com.ex.route_service.entity.CourierRouteEvent;
import com.ex.route_service.enums.CourierRouteEventStatus;
import com.ex.route_service.enums.CourierStatus;
import com.ex.route_service.enums.OrderStatus;
import com.ex.route_service.mapper.CourierMapper;
import com.ex.route_service.mapper.CourierRouteEventMapper;
import com.ex.route_service.repository.CourierJdbcRepository;
import com.ex.route_service.repository.CourierRepository;
import com.ex.route_service.repository.CourierRouteEventRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;
    private final CourierRouteEventRepository courierRouteEventRepository;
    private final CourierRouteEventMapper courierRouteEventMapper;
    private final CourierMapper courierMapper;
    private final OpenRouteService openRouteService;
    private final CourierJdbcRepository courierJdbcRepository;
    private final OrderServiceClient orderServiceClient;


    // курьер начинает рабочую смену, добавляется запись о новом действии в бд
    @Transactional
    public void startWork(StartWorkRequestDto dto, UUID courierId) {
//        проверяем, что у курьера нет не завершенных смен
        Courier courier = courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));

        if (!courier.getCourierStatus().equals(CourierStatus.FINISHED)) {
            throw new EntityNotFoundException("У курьера есть незавершенная работа: " + courierId);
        }
//        меняем оба статуса
        courier.setCourierStatus(CourierStatus.READY);
        courierRepository.save(courier);

        CourierRouteEvent newCourierRouteEvent = courierRouteEventMapper.toEntity(dto,
                CourierRouteEventStatus.SHIFT_STARTED, courier);
        courierRouteEventRepository.save(newCourierRouteEvent);
    }

//    пока закомментил
//    public GetCourierResponseDto getCourier(UUID courierId) {
//        Courier courier = courierRepository.findById(courierId).orElseThrow(()
//                -> new EntityNotFoundException("Курьер не найден: " + courierId));
//        return courierMapper.toResponseDto(courier);
//    }

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

// todo ПОДРУБИТЬ ЗДЕСЬ KEYCLOCK

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
                    null, null,
                    longitudeClient, latitudeClient, courier.getTransportType());
        }

        System.out.println(route);
        return route;

    }

//    готово: получить курьера для сервиса заказов
//    готово: получить ближайших курьеров для сервиса заказов(новый заказ)
//    готово: получить маршрут для курьера

//    TODo надо сделать: взять заказ в работу(это будет запрос в сервис заказов, изменяется статус заказа)
//    но потом по rest отправляется запрос сюда, здесь меняется статус курьера и добавляется запись действия
//    мейби реализовать общий метод, который принимает всю инфу и меняет, добавляет что надо
//    там большиство методов ниже так будут работать


    //TODO реализовать вот эти методы, будет заебись: Курьер может:
//Взять заказ в работу
//Прибыл в точку получения заказа
//Получил заказ у отправителя
//Прибыл по адресу доставки
//Доставил заказ клиенту
//Отменить заказ

//        далее отдаем это сервису заказов, он рассылает уведомления ближайшим курьерам, а так же маршрут


}
