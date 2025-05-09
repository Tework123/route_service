package com.ex.route_service.repository;

import com.ex.route_service.entity.WorkShiftSession;
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


}
