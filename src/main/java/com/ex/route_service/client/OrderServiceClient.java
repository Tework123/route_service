package com.ex.route_service.client;

import com.ex.route_service.dto.OrderServiceDto.OrderResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@AllArgsConstructor
public class OrderServiceClient {

    private final RestTemplate restTemplate;

    private final RequestBuilder requestBuilder;

    //    вообще надо вынести в application.yaml, local, и тд


    public OrderResponseDto getOrder(UUID orderId) {
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
