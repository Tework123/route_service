package com.ex.route_service.repository;

import com.ex.route_service.entity.Count;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CountRepository extends JpaRepository<Count, UUID> {

}
