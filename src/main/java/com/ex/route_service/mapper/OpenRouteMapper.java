package com.ex.route_service.mapper;

import com.ex.route_service.dto.OpenRouteServiceDto.GetRouteResponseDto;
import com.ex.route_service.dto.OpenRouteServiceDto.PostRouteResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class OpenRouteMapper {

    public static GetRouteResponseDto postToGetRouteResponseDto(PostRouteResponseDto postDto) {
        if (postDto == null) return null;

        List<GetRouteResponseDto.Feature> features = postDto.getRoutes().stream().map(route -> {
            GetRouteResponseDto.Properties props = GetRouteResponseDto.Properties.builder()
                    .segments(route.getSegments())
                    .summary(route.getSummary())
                    .way_points(route.getWayPoints())
                    .build();

            GetRouteResponseDto.Geometry geometry = null;

            return GetRouteResponseDto.Feature.builder()
                    .type("Feature")
                    .bbox(route.getBbox())
                    .geometry(geometry)
                    .properties(props)
                    .build();
        }).collect(Collectors.toList());

        return GetRouteResponseDto.builder()
                .type("FeatureCollection")
                .bbox(postDto.getBbox())
                .features(features)
                .metadata(postDto.getMetadata())
                .build();
    }
}
