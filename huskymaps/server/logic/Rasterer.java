package huskymaps.server.logic;

import huskymaps.params.RasterRequest;
import huskymaps.params.RasterResult;

import java.util.Objects;

import static huskymaps.utils.Constants.LAT_PER_TILE;
import static huskymaps.utils.Constants.LON_PER_TILE;
import static huskymaps.utils.Constants.MIN_X_TILE_AT_DEPTH;
import static huskymaps.utils.Constants.MIN_Y_TILE_AT_DEPTH;
import static huskymaps.utils.Constants.MIN_ZOOM_LEVEL;
import static huskymaps.utils.Constants.NUM_X_TILES_AT_DEPTH;
import static huskymaps.utils.Constants.NUM_Y_TILES_AT_DEPTH;
import static huskymaps.utils.Constants.ROOT_ULLAT;
import static huskymaps.utils.Constants.ROOT_ULLON;

/** Application logic for the RasterAPIHandler. */
public class Rasterer {

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param request RasterRequest
     * @return RasterResult
     */
    //request: query
    // More generally, at depth D, there are 2 * 4D images with names ranging from dD_x0_y0 to dD_xk_yj,
    // where k is 2D + 1 - 1 and j is 2D - 1 because our map is rectangular.
    //
    //double lat = ROOT_ULLAT - (LAT_PER_TILE[depth] * y右边那个数);
    //  y右边那个数 = (ROOT_ULLAT - lat) / LAT_PER_TILE[depth];
    //double lon = ROOT_ULLON + (LON_PER_TILE[depth] * x右边那个数);
    public static RasterResult rasterizeMap(RasterRequest request) {
        int depth = request.depth;
        //two ways to push the bound back
        //  1. raw lon/lat
        //  2. by tile
        // double ullat2 = Math.min(request.ullat, ROOT_ULLAT);
        // double ullon2 = Math.max(request.ullon, ROOT_ULLON);
        // double lrlat2 = Math.max(request.lrlat, ROOT_LRLAT);
        // double lrlon2 = Math.min(request.lrlon, ROOT_LRLON);

        int ullat = (int) ((ROOT_ULLAT - request.ullat)/ (LAT_PER_TILE[depth])); //y = 2
        int ullon = (int) ((request.ullon - ROOT_ULLON)/ (LON_PER_TILE[depth])); //x = 10
        int lrlat = (int) ((ROOT_ULLAT - request.lrlat)/ (LAT_PER_TILE[depth])); //y = 25
        int lrlon = (int) ((request.lrlon - ROOT_ULLON)/ (LON_PER_TILE[depth])); //x = 36

        if (ullat < 0){
            ullat = 0;
        }else {
            Math.min(ullat, NUM_Y_TILES_AT_DEPTH[depth] - 1);
        }
        if (ullon < 0){
            ullon = 0;
        }else {
            ullon = Math.min(ullon, NUM_X_TILES_AT_DEPTH[depth] - 1);
        }
        if (lrlat < 0){
            lrlat = 0;
        }else {
            lrlat = Math.min(lrlat, NUM_Y_TILES_AT_DEPTH[depth] - 1);
        }
       if (lrlon < 0){
           lrlon = 0;
       } else {
           lrlon = Math.min(lrlon, NUM_X_TILES_AT_DEPTH[depth] - 1);
       }


        //public static final double ROOT_ULLAT = 47.754097979680026;
        //    public static final double ROOT_ULLON = -122.6953125;
        //    public static final double ROOT_LRLAT = 47.51720069783939;
        //    public static final double ROOT_LRLON = -121.9921875;

        Tile[][] grid = new Tile[lrlat - ullat + 1][lrlon - ullon+ 1];
        for (int i = 0; i <= lrlat - ullat; i++){//y
            for (int j = 0; j <= lrlon - ullon; j++){//x
                grid[i][j] = new Tile(depth, ullon + j, ullat + i);
            }
        }
        return new RasterResult(grid);
    }

    public static class Tile {
        public final int depth;
        public final int x;
        public final int y;

        public Tile(int depth, int x, int y) {
            this.depth = depth;
            this.x = x;
            this.y = y;
        }

        public Tile offset() {
            return new Tile(depth, x + 1, y + 1);
        }

        /**
         * Return the latitude of the upper-left corner of the given slippy map tile.
         * @return latitude of the upper-left corner
         * @source https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
         */
        public double lat() {
            double n = Math.pow(2.0, MIN_ZOOM_LEVEL + depth);
            int slippyY = MIN_Y_TILE_AT_DEPTH[depth] + y;
            double latRad = Math.atan(Math.sinh(Math.PI * (1 - 2 * slippyY / n)));
            return Math.toDegrees(latRad);
        }

        /**
         * Return the longitude of the upper-left corner of the given slippy map tile.
         * @return longitude of the upper-left corner
         * @source https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
         */
        public double lon() {
            double n = Math.pow(2.0, MIN_ZOOM_LEVEL + depth);
            int slippyX = MIN_X_TILE_AT_DEPTH[depth] + x;
            return slippyX / n * 360.0 - 180.0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Tile tile = (Tile) o;
            return depth == tile.depth &&
                    x == tile.x &&
                    y == tile.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(depth, x, y);
        }

        @Override
        public String toString() {
            return "d" + depth + "_x" + x + "_y" + y + ".jpg";
        }
    }
}
