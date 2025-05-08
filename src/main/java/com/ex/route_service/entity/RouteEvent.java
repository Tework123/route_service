package com.ex.route_service.entity;

import com.ex.route_service.enums.RouteEventStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

// Если хочешь логировать действия курьера и статус маршрута — пригодится.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "route_event")
public class RouteEvent {

    @Id
    @Column(name = "work_shift_session_id", nullable = false, updatable = false)
    private UUID routeEventId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "work_shift_session_id")
    private WorkShiftSession workShiftSession;

    @Enumerated(EnumType.STRING)
    private RouteEventStatus routeEventStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "message")
    private String message;

}