package com.ex.route_service.entity;

import com.ex.route_service.enums.CourierRouteEventStatus;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Если хочешь логировать действия курьера и статус маршрута — пригодится.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courier_route_event")
public class CourierRouteEvent {

    @Id
    @Column(name = "courier_route_event_id", nullable = false, updatable = false)
    private UUID courierRouteEventId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @Column(name = "location", columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point location;

    //    свзяь с сервисом заказов, у маршрута может быть заказ, чтобы можно было
    @Column(name = "order_id", nullable = true)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private CourierRouteEventStatus courierRouteEventStatus;

    /**
     * Время создания записи о ивенте на смартфоне.
     */
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Column(name = "time_create", nullable = false, updatable = false)
    private LocalDateTime timeCreate;

    @Column(name = "message")
    private String message;

    @PrePersist
    public void prePersist() {
        timeCreate = LocalDateTime.now();
    }

}