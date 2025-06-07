package com.ex.route_service.client;

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
public class OrderServiceClient {

    private final RestTemplate restTemplate;

    private final RequestBuilder requestBuilder;

    @Deprecated
    public OrderResponseDto getOrder(UUID orderId) {
        if (orderId == null) {
            return null;
        }
        return OrderResponseDto.builder()
                .orderId(orderId)
                .orderStatus("CONFIRMED")
                .build();
    }

    public OrderResponseDto getOrder1(UUID orderId) {
        if (orderId == null) {
            return null;
        }
        String url = requestBuilder.buildUrl(
                "http",
                "order_service:8080",
                "/order/",
                orderId.toString(),
                null
        );

        HttpHeaders headers = requestBuilder.buildHeaders(null);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<OrderResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                OrderResponseDto.class
        );

        return response.getBody();
    }
}
