package com.ex.route_service.client;

import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.ex.route_service.dto.OrderServiceDto.OrderResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@AllArgsConstructor
public class FinanceServiceClient {

    private final RestTemplate restTemplate;

    private final RequestBuilder requestBuilder;

    //    вообще надо вынести в application.yaml, local, и тд

    //    mock вызова сервиса заказов
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

//   TODO  надо ли возвращать что-то? для логирования?
    public OrderResponseDto sendRouteEvents(SendRouteEventsRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        String url = requestBuilder.buildUrl(
                "http",
                "finance_service:8080",
                "/finance/",
                requestDto.toString(),
                null
        );

        HttpHeaders headers = requestBuilder.buildHeaders(null);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<OrderResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                OrderResponseDto.class
        );

        return response.getBody();
    }
}
