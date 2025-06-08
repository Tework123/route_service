package com.ex.route_service.entity;

import com.ex.route_service.enums.CourierStatus;
import com.ex.route_service.enums.TransportType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Сущность Курьера.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courier")
public class Courier {

    @Id
    @GeneratedValue
    @Column(name = "courier_id")
    private UUID courierId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "courier")
    private List<LocationPoint> locationPoints = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "courier")
    private List<RouteEvent> routeEvents = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type", nullable = false)
    private TransportType transportType;

    @Enumerated(EnumType.STRING)
    private CourierStatus courierStatus;
}