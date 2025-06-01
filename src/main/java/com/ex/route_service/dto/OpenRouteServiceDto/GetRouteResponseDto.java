package com.ex.route_service.dto.OpenRouteServiceDto;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetRouteResponseDto {
    private String type;
    private List<Double> bbox;
    private List<Feature> features;
    private Metadata metadata;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Feature {
        private String type;
        private List<Double> bbox;
        private Geometry geometry;
        private Properties properties;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Geometry {
        private String type;
        private List<List<Double>> coordinates;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Properties {
        private List<Segment> segments;
        private List<Integer> way_points;
        private Summary summary;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Segment {
        private double distance;
        private double duration;
        private List<Step> steps;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Step {
        private double distance;
        private double duration;
        private int type;
        private String instruction;
        private String name;
        private List<Integer> way_points;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Summary {
        private double distance;
        private double duration;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Metadata {
        private String attribution;
        private String service;
        private long timestamp;
        private Query query;
        private Engine engine;
    }

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