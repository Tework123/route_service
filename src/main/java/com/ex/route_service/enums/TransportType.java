package com.ex.route_service.enums;

import lombok.Getter;

@Getter
public enum TransportType {
    FOOT("foot-walking"),
    BIKE("cycling-regular"),
    CAR("driving-car");

    private final String openRouteName;

    TransportType(String openRouteName) {
        this.openRouteName = openRouteName;
    }

}