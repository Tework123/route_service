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
    private MetadataDto metadata;

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
        private List<SegmentDto> segments;
        private List<Integer> way_points;
        private SummaryDto summary;
    }

}