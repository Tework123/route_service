package com.ex.route_service.repository;

import com.ex.route_service.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourierRepository extends JpaRepository<Courier, UUID> {

    //    фильтруем курьеров по статусу, транспорту и местоположению
//     под вопросом это запрос, не можем вернуть вместе с курьером общее расстояние
//    @Query(value = """
//            SELECT *, (
//                ST_DistanceSphere(currentLocation, ST_MakePoint(:longitudeRestaurant, :latitudeRestaurant)) +
//                ST_DistanceSphere(ST_MakePoint(:longitudeRestaurant, :latitudeRestaurant), ST_MakePoint(:longitudeClient, :latitudeClient))
//            ) AS total_distance
//            FROM courier
//            WHERE courierStatus = 'READY'
//              AND (
//                (transportType = 'foot' AND (
//                    ST_DistanceSphere(currentLocation, ST_MakePoint(:longitudeRestaurant, :latitudeRestaurant)) +
//                    ST_DistanceSphere(ST_MakePoint(:longitudeRestaurant, :latitudeRestaurant), ST_MakePoint(:longitudeClient, :latitudeClient))
//                ) < :maxFootDistance)
//
//                OR
//
//                (transportType = 'bike' AND (
//                    ST_DistanceSphere(currentLocation, ST_MakePoint(:longitudeRestaurant, :latitudeRestaurant)) +
//                    ST_DistanceSphere(ST_MakePoint(:longitudeRestaurant, :latitudeRestaurant), ST_MakePoint(:longitudeClient, :latitudeClient))
//                ) < :maxBikeDistance)
//
//                OR
//
//                (transportType = 'car' AND (
//                    ST_DistanceSphere(currentLocation, ST_MakePoint(:longitudeRestaurant, :latitudeRestaurant)) +
//                    ST_DistanceSphere(ST_MakePoint(:longitudeRestaurant, :latitudeRestaurant), ST_MakePoint(:longitudeClient, :latitudeClient))
//                ) < :maxCarDistance)
//              )
//            ORDER BY total_distance
//            LIMIT 20
//            """, nativeQuery = true)
//    List<Courier> getCouriersForOrder(
//            @Param("latitudeClient") double latitudeClient,
//            @Param("longitudeClient") double longitudeClient,
//            @Param("latitudeRestaurant") double latitudeRestaurant,
//            @Param("longitudeRestaurant") double longitudeRestaurant,
//            @Param("maxFootDistance") double maxFootDistance,
//            @Param("maxBikeDistance") double maxBikeDistance,
//            @Param("maxCarDistance") double maxCarDistance
//    );

}
