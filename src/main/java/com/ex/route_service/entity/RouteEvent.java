package com.ex.route_service.entity;

import com.ex.route_service.enums.RouteEventStatus;
import com.ex.route_service.enums.WeatherStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность событий маршрута курьера.
 * Используется для логирования действий курьера.
 */@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "route_event")
public class RouteEvent {

    @Id
    @GeneratedValue
    @Column(name = "route_event_id")
    private UUID routeEventId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @OneToOne(optional = false)
    @JoinColumn(name = "location_point_id", referencedColumnName = "location_point_id", nullable = false)
    private LocationPoint locationPoint;

    @Column(name = "order_id")
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private RouteEventStatus routeEventStatus;

    @Enumerated(EnumType.STRING)
    private WeatherStatus weatherStatus;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @CreationTimestamp
    @Column(name = "time_create", nullable = false, updatable = false)
    private LocalDateTime timeCreate;

    @Column(name = "message")
    private String message;
}