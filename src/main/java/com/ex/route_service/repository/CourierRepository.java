package com.ex.route_service.repository;

import com.ex.route_service.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link Courier}.
 */
@Repository
public interface CourierRepository extends JpaRepository<Courier, UUID> {

}
