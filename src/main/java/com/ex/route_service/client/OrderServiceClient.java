package com.ex.route_service.client;

import com.ex.route_service.dto.OrderServiceDto.OrderResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * Клиент для взаимодействия с сервисом заказов.
 * Позволяет получать информацию о заказах.
 */
@Slf4j
@Component
@AllArgsConstructor
public class OrderServiceClient {

    private final RestTemplate restTemplate;

    private final RequestBuilder requestBuilder;

    /**
     * Заглушка для получения информации о заказе по ID.
     * Возвращает тестовый объект со статусом CONFIRMED.
     *
     * @param orderId идентификатор заказа
     * @return OrderResponseDto с заданным ID и статусом
     * @deprecated метод-заглушка, не предназначен для использования в продакшене
     */
    @Deprecated
    public OrderResponseDto getOrder(UUID orderId) {
        if (orderId == null) {
            return null;
        }
        return OrderResponseDto.builder()
                .orderId(orderId)
                .orderStatus("DELIVERING")
                .build();
    }

    /**
     * Получить информацию о заказе по ID.
     *
     * @param orderId идентификатор заказа
     * @return DTO с информацией о заказе
     */
    public OrderResponseDto getOrder1(UUID orderId) {
        if (orderId == null) {
            log.warn("Вызов getOrder1 с null orderId");
            return null;
        }

        String url = requestBuilder.buildUrl(
                "http",
                "order_service:8080",
                "/order/",
                orderId.toString(),
                null
        );

        log.info("Запрос в сервис заказов для orderId: {}, URL: {}", orderId, url);

        HttpHeaders headers = requestBuilder.buildHeaders(null);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<OrderResponseDto> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, OrderResponseDto.class);
            log.info("Успешно получен заказ с id: {}", orderId);
            return response.getBody();
        } catch (Exception e) {
            log.error("Ошибка при получении заказа с id: {}", orderId, e);
            throw e;
        }
    }
}
