package com.ex.route_service.dto.OpenRouteServiceDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostRouteResponseDto {
    private List<Double> bbox;
    private List<Route> routes;
    private MetadataDto metadata;

    @Data
    @NoArgsConstructor
    public static class Route {
        private SummaryDto summary;
        private List<SegmentDto> segments;
        private List<Double> bbox;
        private String geometry;
        @JsonProperty("way_points")
        private List<Integer> wayPoints;
    }

}