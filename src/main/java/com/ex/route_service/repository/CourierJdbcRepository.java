package com.ex.route_service.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CourierJdbcRepository {
    private final JdbcTemplate  jdbcTemplate;

    public CourierJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> findNearbyCouriersRaw(
            double latitudeClient,
            double longitudeClient,
            double latitudeRestaurant,
            double longitudeRestaurant,
            double maxFootDistance,
            double maxBikeDistance,
            double maxCarDistance
    ) {
        String sql = """
                SELECT courier_id, transport_type,
                       ST_Y(current_location) AS latitude,
                       ST_X(current_location) AS longitude,
                       (
                           ST_DistanceSphere(current_location, ST_MakePoint(?, ?)) +
                           ST_DistanceSphere(ST_MakePoint(?, ?), ST_MakePoint(?, ?))
                       ) AS total_distance
                FROM courier
                WHERE courier_status = 'READY'
                  AND (
                    (transport_type = 'foot' AND (
                        ST_DistanceSphere(current_location, ST_MakePoint(?, ?)) +
                        ST_DistanceSphere(ST_MakePoint(?, ?), ST_MakePoint(?, ?))
                    ) < ?)
                    OR
                    (transport_type = 'bike' AND (
                        ST_DistanceSphere(current_location, ST_MakePoint(?, ?)) +
                        ST_DistanceSphere(ST_MakePoint(?, ?), ST_MakePoint(?, ?))
                    ) < ?)
                    OR
                    (transport_type = 'car' AND (
                        ST_DistanceSphere(current_location, ST_MakePoint(?, ?)) +
                        ST_DistanceSphere(ST_MakePoint(?, ?), ST_MakePoint(?, ?))
                    ) < ?)
                  )
                ORDER BY total_distance
                LIMIT 20
            """;

        return jdbcTemplate.queryForList(sql,
                // Параметры в том же порядке, как и ? в SQL
                longitudeRestaurant, latitudeRestaurant,
                longitudeRestaurant, latitudeRestaurant, longitudeClient, latitudeClient,

                longitudeRestaurant, latitudeRestaurant,
                longitudeRestaurant, latitudeRestaurant, longitudeClient, latitudeClient,
                maxFootDistance,

                longitudeRestaurant, latitudeRestaurant,
                longitudeRestaurant, latitudeRestaurant, longitudeClient, latitudeClient,
                maxBikeDistance,

                longitudeRestaurant, latitudeRestaurant,
                longitudeRestaurant, latitudeRestaurant, longitudeClient, latitudeClient,
                maxCarDistance
        );
    }
}
