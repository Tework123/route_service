package com.ex.route_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "work_shift_session")
public class WorkShiftSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "work_shift_session_id", nullable = false, updatable = false)
    private UUID workShiftSessionId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "time_create", nullable = false)
    private LocalDateTime timeCreate;

    @PrePersist
    public void prePersist() {
        timeCreate = LocalDateTime.now();
    }

}