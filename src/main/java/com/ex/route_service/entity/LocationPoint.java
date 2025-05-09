package com.ex.route_service.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_point_id", nullable = false, updatable = false)
    private UUID locationPointId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "work_shift_session_id")
    private WorkShiftSession workShiftSession;


    //    TODO OneToMany с routeEvent

    /**
     * Широта (latitude) точки координат.
     */
    @Column(name = "latitude", nullable = false)
    private double latitude;

    /**
     * Долгота (longitude) точки координат.
     */
    @Column(name = "longitude", nullable = false)
    private double longitude;

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