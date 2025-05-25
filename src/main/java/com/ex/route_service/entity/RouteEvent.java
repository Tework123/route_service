package com.ex.route_service.entity;

import com.ex.route_service.enums.RouteEventStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

// Если хочешь логировать действия курьера и статус маршрута — пригодится.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "route_event")
public class RouteEvent {

    @Id
    @Column(name = "route_event_id", nullable = false, updatable = false, columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID routeEventId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courier_id")
    private Courier courier;

//    у ивента всегда есть координаты
    @OneToOne(optional = false)
    @JoinColumn(name = "location_point_id", referencedColumnName = "location_point_id" , nullable = false)
    private LocationPoint locationPoint;

    //    свзяь с сервисом заказов, у маршрута может быть заказ, чтобы можно было
    @Column(name = "order_id", nullable = true)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private RouteEventStatus routeEventStatus;

    /**
     * Время создания записи об ивенте на смартфоне.
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