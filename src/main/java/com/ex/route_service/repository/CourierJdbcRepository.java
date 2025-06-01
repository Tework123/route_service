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
            double longitudeClient, double latitudeClient,
            double longitudeRestaurant, double latitudeRestaurant,
            double maxFootDistance,
            double maxBikeDistance,
            double maxCarDistance
    ) {
        String sql = """
        SELECT c.courier_id, c.transport_type,
               ST_X(lp.location) AS longitude,
               ST_Y(lp.location) AS latitude,

               (
                   ST_DistanceSphere(lp.location, ST_MakePoint(?, ?)) +
                   ST_DistanceSphere(ST_MakePoint(?, ?), ST_MakePoint(?, ?))
               ) AS total_distance

        FROM courier c
        JOIN (
            SELECT DISTINCT ON (courier_id)
                   courier_id, location, timestamp
              FROM location_point
             ORDER BY courier_id, timestamp DESC
        ) lp ON lp.courier_id = c.courier_id

        WHERE c.courier_status = 'READY'
          AND (
            (c.transport_type = 'FOOT' AND (
                ST_DistanceSphere(lp.location, ST_MakePoint(?, ?)) +
                ST_DistanceSphere(ST_MakePoint(?, ?), ST_MakePoint(?, ?))
            ) < ?)
            OR
            (c.transport_type = 'BIKE' AND (
                ST_DistanceSphere(lp.location, ST_MakePoint(?, ?)) +
                ST_DistanceSphere(ST_MakePoint(?, ?), ST_MakePoint(?, ?))
            ) < ?)
            OR
            (c.transport_type = 'CAR' AND (
                ST_DistanceSphere(lp.location, ST_MakePoint(?, ?)) +
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
