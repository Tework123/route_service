package com.ex.route_service.client;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
@AllArgsConstructor
public class RequestBuilder {

    public String buildUrl(String scheme, String host, String path, String pathVariable, Map<String, String> queryParams) {

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .path(path.endsWith("/") ? path : path + "/")
                .path(pathVariable);

        if (queryParams != null && !queryParams.isEmpty()) {
            queryParams.forEach(builder::queryParam);
        }
        return builder.toUriString();
    }

    public HttpHeaders buildHeaders(MediaType mediaType) {
        if (mediaType == null) {
            return new HttpHeaders();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return headers;

    }


}
