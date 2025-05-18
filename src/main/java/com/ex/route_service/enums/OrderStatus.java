package com.ex.route_service.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum OrderStatus {
    CONFIRMED("Confirmed"),
    PREPARING("Preparing"),
    READY_FOR_PICKUP("Ready for Pickup"),
    CANCELLED("Cancelled"),
    DELIVERED("Delivered");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public static Optional<OrderStatus> from(String status) {
        return Arrays.stream(values())
                .filter(s -> s.value.equalsIgnoreCase(status))
                .findFirst();
    }
}