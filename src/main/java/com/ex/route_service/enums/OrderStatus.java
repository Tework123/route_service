package com.ex.route_service.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum OrderStatus {
    //    создан клиентом
    CREATED("Created"),
    //    ожидает подтверждения рестораном
    PENDING("Pending"),
    //    подтвержден рестораном
    CONFIRMED("Confirmed"),
    //    готовиться в ресторане
    PREPARING("Preparing"),
    //    готов, ждет курьера
    READY_FOR_PICKUP("Ready for Pickup"),
    //    курьер забрал, доставляет
    DELIVERING("Delivering"),
    //    доставлен
    DELIVERED("Delivered"),

    CANCELLED_BY_RESTAURANT("Cancelled by Restaurant"),
    CANCELLED_BY_SYSTEM("Cancelled by System"),
    CANCELLED_BY_CUSTOMER("Cancelled by Customer");


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