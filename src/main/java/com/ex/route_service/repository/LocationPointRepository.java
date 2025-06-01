package com.ex.route_service.repository;

import com.ex.route_service.entity.LocationPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link LocationPoint}.
 */
@Repository
public interface LocationPointRepository extends JpaRepository<LocationPoint, UUID> {

    @Query("""
    SELECT lp FROM LocationPoint lp
    WHERE lp.courier.courierId = :courierId
    ORDER BY lp.timestamp DESC
""")
    List<LocationPoint> findLatestByCourierId(@Param("courierId") UUID courierId, Pageable pageable);

    /**
     * Возвращает список координат для заданного устройства за указанный временной интервал.
     *
     * @param deviceId идентификатор устройства
     * @param from     начало временного интервала (включительно)
     * @param to       конец временного интервала (включительно)
     * @return список точек, отсортированных по времени в порядке возрастания
     */
//    List<LocationPoint> findByDeviceIdAndTimeCreateBetweenOrderByTimeCreateAsc(
//            String deviceId,
//            LocalDateTime from,
//            LocalDateTime to
//    );
}