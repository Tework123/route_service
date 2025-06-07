package com.ex.route_service.dto.OpenRouteServiceDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummaryDto {
    private double distance;
    private double duration;
}
