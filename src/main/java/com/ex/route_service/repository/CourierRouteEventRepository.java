package com.ex.route_service.repository;

import com.ex.route_service.entity.CourierRouteEvent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourierRouteEventRepository extends JpaRepository<CourierRouteEvent, UUID> {
//     находим последний статус курьера
    @Query("SELECT e FROM CourierRouteEvent e WHERE e.courier.courierId = :courierId ORDER BY e.timestamp DESC")
    CourierRouteEvent findCourierLastStatus(@Param("courierId") UUID courierId);


}
