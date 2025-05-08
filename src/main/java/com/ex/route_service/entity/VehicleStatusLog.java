package com.ex.route_service.entity;

import com.ex.route_service.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

// если хочешь следить за использованием/доступностью ТС или выявлять нерабочие.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vehicle_status_log")
public class VehicleStatusLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vehicle_status_log_id", nullable = false, updatable = false)
    private UUID vehicleStatusLogId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    private VehicleStatus vehicleStatus;

    @Column(name = "time_create", nullable = false)
    private LocalDateTime timeCreate;

    @PrePersist
    public void prePersist() {
        timeCreate = LocalDateTime.now();
    }

}
