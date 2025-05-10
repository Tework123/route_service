package com.ex.route_service.repository;

import com.ex.route_service.entity.WorkShiftSession;
import com.ex.route_service.enums.WorkShiftSessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkShiftSessionRepository extends JpaRepository<WorkShiftSession, UUID> {

    @Query("SELECT w FROM WorkShiftSession w WHERE w.userId = :userId AND w.workShiftSessionStatus <> 'ABORTED'")
    List<WorkShiftSession> findAllByUserIdAndStatusNotAborted(@Param("userId") UUID userId);


    @Query(value = """
                SELECT s.*
                FROM work_shift_session s
                JOIN location_point lp ON lp.work_shift_session_id = s.work_shift_session_id
                WHERE s.work_shift_session_status = 'READY'
                  AND lp.time_create = (
                      SELECT MAX(lp2.time_create)
                      FROM location_point lp2
                      WHERE lp2.work_shift_session_id = s.work_shift_session_id
                  )
                    AND lp.time_create >= NOW() - make_interval(mins := :minutesAgo)
                      AND ST_DistanceSphere(
                        ST_MakePoint(lp.longitude, lp.latitude),
                        ST_MakePoint(:longitude, :latitude)
                      ) <= :distanceMeters
            """, nativeQuery = true)
    List<WorkShiftSession> findAllReadyWorkShifts(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("minutesAgo") int minutesAgo,
            @Param("distanceMeters") double distanceMeters
    );
}
