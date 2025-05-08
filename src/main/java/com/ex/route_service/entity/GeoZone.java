package com.ex.route_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Polygon;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "geozone")
public class GeoZone {

    @Id
    @Column(name = "geozone_id", nullable = false, updatable = false)
    private UUID geoZoneId;

    /**
     * Название геозоны.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Геометрия зоны в формате Polygon (PostGIS)
     */
    @Column(columnDefinition = "geometry(Polygon, 4326)", nullable = false)
    private Polygon geometry;

    /**
     * Время создания геозоны.
     */
    @Column(name = "time_create", nullable = false, updatable = false)
    private LocalDateTime timeCreate;

    @PrePersist
    public void prePersist() {
        if (geoZoneId == null) {
            geoZoneId = UUID.randomUUID();
        }
        timeCreate = LocalDateTime.now();
    }
}
