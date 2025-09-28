package com.ex.route_service.mapper;

import com.ex.route_service.dto.RouteServiceDto.CountDto;
import com.ex.route_service.entity.Count;

public class CountMapper {

    public static CountDto toDto(Count count) {
        return count == null ? null : CountDto.builder()
                .id(count.getId())
                .count(count.getCount())
                .routeEventId(count.getRouteEventId())
                .countStatus(count.getCountStatus())
                .createDt(count.getCreateDt())
                .build();

    }
}
