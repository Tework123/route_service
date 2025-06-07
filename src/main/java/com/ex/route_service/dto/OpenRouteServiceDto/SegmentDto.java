package com.ex.route_service.dto.OpenRouteServiceDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SegmentDto {
    private double distance;
    private double duration;
    private List<StepDto> steps;
}
