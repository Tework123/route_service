package com.ex.route_service.entity;

import com.ex.route_service.enums.CourierStatus;
import com.ex.route_service.enums.TransportType;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courier")
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courier_id", nullable = false, updatable = false)
    private UUID courierId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "courier")
    private List<CourierLocationPoint> courierLocationPoints = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "courier")
    private List<CourierRouteEvent> courierRouteEvents = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type", nullable = false)
    private TransportType transportType;

    @Enumerated(EnumType.STRING)
    private CourierStatus courierStatus;

    @Column(name = "current_location", columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point currentLocation;

}