package com.ex.route_service.repository;

import com.ex.route_service.entity.LocationPoint;
import com.ex.route_service.entity.RouteEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RouteEventRepository extends JpaRepository<RouteEvent, UUID> {
}
