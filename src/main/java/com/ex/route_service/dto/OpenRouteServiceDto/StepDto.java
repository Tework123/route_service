package com.ex.route_service.dto.OpenRouteServiceDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StepDto {
    private double distance;
    private double duration;
    private int type;
    private String instruction;
    private String name;
    private List<Integer> way_points;
}
