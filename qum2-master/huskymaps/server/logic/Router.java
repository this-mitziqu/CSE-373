package huskymaps.server.logic;

import astar.AStarSolver;
import huskymaps.StreetMapGraph;
import huskymaps.params.RouteRequest;

import java.util.List;

/** Application logic for the RoutingAPIHandler. */
public class Router {

    /**
     * Overloaded method for shortestPath that has flexibility to specify a solver
     * and returns a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination location.
     * @param g The graph to use.
     * @param request The requested route.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(StreetMapGraph g, RouteRequest request) {
         long src = g.closest(request.startLat, request.startLon);
         long dest = g.closest(request.endLat, request.endLon);
         System.out.println("" + src + ", " + dest);
         return new AStarSolver<>(g, src, dest, 20).solution();
    }


    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigationDirections for the route.
     */
    public static List<NavigationDirection> routeDirections(StreetMapGraph g, List<Long> route) {
        // Optional
        return null;
    }
}
