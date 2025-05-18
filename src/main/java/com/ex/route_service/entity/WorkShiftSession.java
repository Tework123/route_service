//package com.ex.route_service.entity;
//
//import com.ex.route_service.enums.RouteEventStatus;
//import com.ex.route_service.enums.WorkShiftSessionStatus;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@Table(name = "work_shift_session")
//public class WorkShiftSession {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "work_shift_session_id", nullable = false, updatable = false)
//    private UUID workShiftSessionId;
//
//    @Column(name = "user_id", nullable = false, updatable = false)
//    private UUID userId;
//
////    выпилил транспорт
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
//            orphanRemoval = true, mappedBy = "workShiftSession")
//    private List<LocationPoint> locationPointList = new ArrayList<>();
//
//    @Column(name = "start_time", nullable = false)
//    private LocalDateTime startTime;
//
//    @Column(name = "end_time")
//    private LocalDateTime endTime;
//
//    @Enumerated(EnumType.STRING)
//    private WorkShiftSessionStatus workShiftSessionStatus;
//
//    @Column(name = "time_create", nullable = false)
//    private LocalDateTime timeCreate;
//
//    @PrePersist
//    public void prePersist() {
//        timeCreate = LocalDateTime.now();
//    }
//
//}