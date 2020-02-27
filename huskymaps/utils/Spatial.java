package huskymaps.utils;

import static huskymaps.utils.Constants.K0;
import static huskymaps.utils.Constants.R;
import static huskymaps.utils.Constants.ROOT_LAT;
import static huskymaps.utils.Constants.ROOT_LON;

/**
 * Utility methods for interfacing between coordinate systems. Because the
 * Earth is fairly spherical, longitudes and latitudes are curved so it's not
 * appropriate for the k-d tree to compare coordinates. The k-d tree needs to
 * have points projected down to a 2-dimensional grid.
 */
public class Spatial {

    /**
     * Returns the euclidean distance (L2 norm) squared between two points
     * (x1, y1) and (x2, y2). Note: This is the square of the Euclidean distance.
     * @param x1 The x-coordinate of the first point.
     * @param x2 The x-coordinate of the second point.
     * @param y1 The y-coordinate of the first point.
     * @param y2 The y-coordinate of the second point.
     * @return The squared euclidean distance between the two points.
     */
    public static double euclideanDistance(double x1, double x2, double y1, double y2) {
        return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
    }

    /**
     * Returns the great-circle (haversine) distance between geographic coordinates
     * (LATV, LONV) and (LATW, LONW).
     * @param lonV  The longitude of the first vertex.
     * @param latV  The latitude of the first vertex.
     * @param lonW  The longitude of the second vertex.
     * @param latW  The latitude of the second vertex.
     * @return The great-circle distance between the two vertices.
     * @source https://www.movable-type.co.uk/scripts/latlong.html
     */
    public static double greatCircleDistance(double lonV, double lonW, double latV, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    /**
     * Return the Euclidean x-value for some point, p. Found by computing the
     * Transverse Mercator projection centered on the root.
     * @param lon The longitude for p.
     * @param lat The latitude for p.
     * @return The flattened, Euclidean x-value for p.
     * @source https://en.wikipedia.org/wiki/Transverse_Mercator_projection
     */
    public static double projectToX(double lon, double lat) {
        double dlon = Math.toRadians(lon - ROOT_LON);
        double phi = Math.toRadians(lat);
        double b = Math.sin(dlon) * Math.cos(phi);
        return (K0 / 2) * Math.log((1 + b) / (1 - b));
    }

    /**
     * Return the Euclidean y-value for some point, p. Found by computing the
     * Transverse Mercator projection centered on the root.
     * @param lon The longitude for p.
     * @param lat The latitude for p.
     * @return The flattened, Euclidean y-value for p.
     * @source https://en.wikipedia.org/wiki/Transverse_Mercator_projection
     */
    public static double projectToY(double lon, double lat) {
        double dlon = Math.toRadians(lon - ROOT_LON);
        double phi = Math.toRadians(lat);
        double con = Math.atan(Math.tan(phi) / Math.cos(dlon));
        return K0 * (con - Math.toRadians(ROOT_LAT));
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * @param lonV  The longitude of the first vertex.
     * @param latV  The latitude of the first vertex.
     * @param lonW  The longitude of the second vertex.
     * @param latW  The latitude of the second vertex.
     * @return The initial bearing between the vertices.
     * @source https://www.movable-type.co.uk/scripts/latlong.html
     */
    public static double bearing(double lonV, double lonW, double latV, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }
}
