package com.ex.route_service.dto.OpenRouteServiceDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetadataDto {
    private String attribution;
    private String service;
    private long timestamp;
    private Query query;
    private Engine engine;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Query {
        private List<List<Double>> coordinates;
        private String profile;
        private String profileName;
        private String format;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Engine {
        private String version;
        private String build_date;
        private String graph_date;
    }
}
