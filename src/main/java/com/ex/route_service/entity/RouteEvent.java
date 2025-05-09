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
    @Column(name = "route_event_id", nullable = false, updatable = false)
    private UUID routeEventId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "work_shift_session_id")
    private WorkShiftSession workShiftSession;

    //    TODO manyTOOne с locationPoint


    //    свзяь с сервисом заказов, у маршрута может быть заказ, чтобы можно было
    @Column(name = "order_id", nullable = true)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private RouteEventStatus routeEventStatus;

    @Column(name = "time_create", nullable = false)
    private LocalDateTime timeCreate;

    @Column(name = "message")
    private String message;

}