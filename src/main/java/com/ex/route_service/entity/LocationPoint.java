package com.ex.route_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность "Точка местоположения".
 * Хранит координаты устройства в определённый момент времени
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "location_point")
public class LocationPoint {

    @Id
    @GeneratedValue
    @Column(name = "location_point_id")
    private UUID locationPointId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @OneToOne(mappedBy = "locationPoint")
    private RouteEvent routeEvent;


    @Column(name = "location", columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point location;

    /**
     * Время создания записи о местоположении на смартфоне.
     */
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    /**
     * Время сохранения записи о местоположении в бд.
     */
    @CreationTimestamp
    @Column(name = "time_create", nullable = false, updatable = false)
    private LocalDateTime timeCreate;
}