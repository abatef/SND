package com.snd.storefinder.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private double latitude;
    private double longitude;

    public static Location of(Point point) {
        Location location = new Location();
        location.latitude = point.getX();
        location.longitude = point.getY();
        return location;
    }

    public static Point toPoint(Location location) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        return geometryFactory.createPoint(new Coordinate(location.latitude, location.longitude));
    }

    public Point toPoint() {
        return Location.toPoint(this);
    }
}
