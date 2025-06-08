package com.ex.route_service.client;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Класс для построения URL и HTTP-заголовков.
 */
@Component
@AllArgsConstructor
public class RequestBuilder {

    /**
     * Построить URL.
     *
     * @param scheme       схема (например, "http" или "https")
     * @param host         хост (например, "api.example.com")
     * @param path         базовый путь (например, "/api/v1/resource/")
     * @param pathVariable дополнительная часть пути, например ID ресурса
     * @param queryParams  параметры запроса в виде ключ-значение
     * @return сформированный URL в виде строки
     */
    public String buildUrl(String scheme, String host, String path, String pathVariable, Map<String, String> queryParams) {

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .path(path.endsWith("/") ? path : path + "/");

        if (pathVariable != null) {
            builder.path(pathVariable);
        }

        if (queryParams != null && !queryParams.isEmpty()) {
            queryParams.forEach(builder::queryParam);
        }
        return builder.toUriString();
    }

    /**
     * Построить HTTP-заголовки с указанным Content-Type.
     *
     * @param mediaType тип содержимого (например, MediaType.APPLICATION_JSON)
     * @return объект HttpHeaders
     */
    public HttpHeaders buildHeaders(MediaType mediaType) {
        if (mediaType == null) {
            return new HttpHeaders();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return headers;

    }
}
