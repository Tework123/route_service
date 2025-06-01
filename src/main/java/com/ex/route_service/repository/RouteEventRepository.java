package com.ex.route_service.repository;


import com.ex.route_service.entity.RouteEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RouteEventRepository extends JpaRepository<RouteEvent, UUID> {
    //     находим последний статус курьера
    @Query("SELECT e FROM RouteEvent e WHERE e.courier.courierId = :courierId ORDER BY e.timestamp DESC")
    RouteEvent findCourierLastStatus(@Param("courierId") UUID courierId);

    @Query("SELECT e FROM RouteEvent e WHERE e.orderId = : orderId ORDER BY e.timestamp DESC")
    List<RouteEvent> findAllByOrderId(@Param("orderId") UUID orderId);
}
