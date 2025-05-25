package com.ex.route_service.Utils;

import jakarta.persistence.EntityNotFoundException;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;


public class GeometryUtils {

    //        преобразование Double Longitude и Latitude в Point
    public static Point toPoint(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            throw new EntityNotFoundException("Значения отсутствуют");
        }

        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(longitude, latitude);
        return geometryFactory.createPoint(coordinate);
    }

}
