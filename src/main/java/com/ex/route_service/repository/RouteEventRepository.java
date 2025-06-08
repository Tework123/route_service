package com.ex.route_service.repository;


import com.ex.route_service.entity.RouteEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link RouteEvent}.
 */
@Repository
public interface RouteEventRepository extends JpaRepository<RouteEvent, UUID> {

    /**
     * Находит все события маршрута, связанные с заданным идентификатором заказа.
     *
     * @param orderId UUID заказа
     * @return список событий {@link RouteEvent} для данного заказа
     */
    @Query("SELECT e FROM RouteEvent e WHERE e.orderId = :orderId ORDER BY e.timestamp DESC")
    List<RouteEvent> findAllByOrderId(@Param("orderId") UUID orderId);
}
