package com.ex.route_service.repository;

import com.ex.route_service.entity.LocationPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link LocationPoint}.
 */
@Repository
public interface LocationPointRepository extends JpaRepository<LocationPoint, UUID> {

    /**
     * Находит последнюю (по времени) точку локации курьера по его идентификатору.
     *
     * @param courierId UUID курьера
     * @return последняя {@link LocationPoint} курьера или null, если не найдено
     */
    @Query(value = """
    SELECT * 
    FROM location_point 
    WHERE courier_id = :courierId 
    ORDER BY timestamp DESC 
    LIMIT 1
""", nativeQuery = true)
    LocationPoint findTopByCourierId(@Param("courierId") UUID courierId);

    /**
     * Возвращает список координат для заданного устройства за указанный временной интервал.
     *
     * @param fromDateTime     начало временного интервала (включительно)
     * @param toDateTime       конец временного интервала (включительно)
     * @return список точек, отсортированных по времени в порядке возрастания
     */
    @Query("""
    SELECT lp FROM LocationPoint lp
    WHERE lp.courier.courierId = :courierId
    AND (CAST(:fromDateTime AS timestamp) IS NULL OR lp.timestamp >= :fromDateTime)
    AND (CAST(:toDateTime AS timestamp) IS NULL OR lp.timestamp <= :toDateTime)
    ORDER BY lp.timestamp ASC
""")
    List<LocationPoint> findByCourierIdAndTimestampBetween(
            @Param("courierId") UUID courierId,
            @Param("fromDateTime") LocalDateTime fromDateTime,
            @Param("toDateTime") LocalDateTime toDateTime
    );
}