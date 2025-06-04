package com.ex.route_service.client;

import com.ex.route_service.AppStartupLogger;
import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.ex.route_service.dto.OrderServiceDto.OrderResponseDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@AllArgsConstructor
public class FinanceServiceClient {

    private final RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(AppStartupLogger.class);


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
    public void sendRouteEvents(SendRouteEventsRequestDto requestDto) {
        String url = requestBuilder.buildUrl(
                "http",
                "finance_service:8080",
                "/finance/",
                requestDto.toString(),
                null
        );

        HttpHeaders headers = requestBuilder.buildHeaders(null);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        log.info("Начат процесс отправки ивентов в сервис финансов. orderId={}, courierId={}", requestDto.getOrderId(), requestDto.getCourierId());

        try {
            restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    OrderResponseDto.class
            );

            log.info("Ивенты отправлены в сервис финансов. orderId={}, courierId={}",
                    requestDto.getOrderId(), requestDto.getCourierId());
        } catch (RestClientException ex) {
            log.info("Ошибка при отправке ивентов в сервис финансов. orderId={}, courierId={}," +
                    " причина={}", requestDto.getOrderId(), requestDto.getCourierId(), ex.getMessage()
            );
        }
    }
}
