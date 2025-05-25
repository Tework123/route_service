package com.ex.route_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность "Точка местоположения".
 * Хранит координаты устройства в определённый момент времени, без ивента
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
    @Column(name = "location_point_id", nullable = false, updatable = false, columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID locationPointId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courier_id", nullable = true)
    private Courier courier;

//    у координаты может не быть ивента
    @OneToOne(mappedBy = "locationPoint", optional = true)
    private RouteEvent routeEvent;


    @Column(name = "location", columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point location;

    /**
     * Время создания записи о местоположении на смартфоне.
     */
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    /**
     * Время сохранении записи о местоположении в бд.
     */
    @Column(name = "time_create", nullable = false, updatable = false)
    private LocalDateTime timeCreate;

    @PrePersist
    public void prePersist() {
        timeCreate = LocalDateTime.now();
    }
}