package huskymaps.params;

import org.apache.commons.math3.util.Precision;

import java.util.Objects;

import static huskymaps.utils.Constants.DECIMAL_PLACES;
import static huskymaps.utils.Constants.EPSILON;

public class Location {
    protected final double lat;
    protected final double lon;
    protected final String name;

    public Location(double lat, double lon, String name) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public double lat() {
        return lat;
    }

    public double lon() {
        return lon;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return Precision.equals(location.lat, lat, EPSILON) &&
                Precision.equals(location.lon, lon, EPSILON) &&
                Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                Precision.round(lat, DECIMAL_PLACES),
                Precision.round(lon, DECIMAL_PLACES),
                name
        );
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", name='" + name + '\'' +
                '}';
    }
}
