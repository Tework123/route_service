package com.ex.route_service.entity;

import com.ex.route_service.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Сущность, представляющая зарегистрированное устройство, отправляющее координаты.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vehicle_id", nullable = false, updatable = false)
    private UUID vehicleId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "vehicle")
    private List<LocationPoint> locationPointList = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gov_number", nullable = false)
    private String govNumber;

    @Enumerated(EnumType.STRING)
    private VehicleStatus vehicleStatus;

    @Setter(AccessLevel.NONE)
    @Column(name = "time_create", nullable = false, updatable = false)
    private LocalDateTime timeCreate;

    @Column(name = "time_last_seen_at")
    private LocalDateTime timeLastSeenAt;

    @PrePersist
    public void prePersist() {
        timeCreate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        timeLastSeenAt = LocalDateTime.now();
    }
}
