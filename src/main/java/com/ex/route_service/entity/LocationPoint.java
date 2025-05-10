package com.ex.route_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_point_id", nullable = false, updatable = false)
    private UUID locationPointId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "work_shift_session_id", nullable = false)
    private WorkShiftSession workShiftSession;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "locationPoint")
    private List<RouteEvent> routeEventList = new ArrayList<>();

//    /**
//     * Широта (latitude) точки координат.
//     */
//    @Column(name = "latitude", nullable = false)
//    private double latitude;
//
//    /**
//     * Долгота (longitude) точки координат.
//     */
//    @Column(name = "longitude", nullable = false)
//    private double longitude;

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