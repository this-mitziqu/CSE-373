package huskymaps;



import astar.AStarGraph;
import astar.WeightedEdge;
import autocomplete.BinaryRangeSearch;
import autocomplete.Term;
import kdtree.KDTreePointSet;
import kdtree.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static huskymaps.utils.Spatial.greatCircleDistance;
import static huskymaps.utils.Spatial.projectToX;
import static huskymaps.utils.Spatial.projectToY;

public class StreetMapGraph implements AStarGraph<Long> {
    private Map<Long, Node> nodes = new HashMap<>();
    private Map<Long, Set<WeightedEdge<Long>>> neighbors = new HashMap<>();
    private List<Point> list;
    private Map<Long, Point> idToPoint;
    private Map<Point, Long> pointToId;
    private Map<Point, Node> nameMap;
    private KDTreePointSet kd;

    public StreetMapGraph(String filename) {
        OSMGraphHandler.initializeFromXML(this, filename);
        List<Point> result = new ArrayList<>();
        for (Long v : idToPoint.keySet()){
            if (isNavigable(nodes.get(v))){
                result.add(idToPoint.get(v));
            }
        }
        kd = new KDTreePointSet(result);
        list = new LinkedList<>();
        idToPoint = new HashMap<>();
        nameMap = new HashMap<>();
        pointToId = new HashMap<>();
        for (Long v: vertices()){
            //经纬度搞反
            list.add(new Point(projectToX(lon(v),  lat(v)), projectToY(lon(v), lat(v)))); //point
            idToPoint.put(v, (new Point(projectToX(lon(v), lat(v)), projectToY(lon(v), lat(v))))); // point => id
            pointToId.put((new Point(projectToX(lon(v), lat(v)), projectToY(lon(v), lat(v)))), v);
            nameMap.put(new Point(projectToX(lon(v), lat(v)), projectToY(lon(v), lat(v))), getNode(v)); // point => node

        }
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lat The target latitude.
     * @param lon The target longitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lat, double lon) {
        double x = projectToX(lon, lat);
        double y = projectToY(lon, lat);

        // Use x and y, not lon and lat, when working with Point instances
        Point nearestPoint = kd.nearest(x, y);
        return pointToId.get(nearestPoint);
    }

    /**
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of full names of locations matching the <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<Term> result = new ArrayList<>();
        for (Node n: nameMap.values()){
            if (n.name() != null) {
                result.add(new Term(n.name(), n.importance));
            }
        }
        Term[] names = new Term[result.size()];
        int i = 0;
        for (Term name: result){
            names[i] = name;
            i++;
        }
        BinaryRangeSearch brs = new BinaryRangeSearch(names);
        names = brs.allMatches(prefix);
        List<String> result2 = new ArrayList<>();
        for (int j = 0; j < names.length; j++){
            result2.add(names[j].query());
        }
        return result2;
    }



    /**
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose name matches the <code>locationName</code>.
     */
    public List<Node> getLocations(String locationName) {
        List<Node> result = new LinkedList<>();
        for (Node n: nameMap.values()){
            if (n.name() != null && n.name().equals(locationName)){
                result.add(n);
            }
        }
        return result;
    }

    /** Returns a list of outgoing edges for V. Assumes V exists in this graph. */
    @Override
    public List<WeightedEdge<Long>> neighbors(Long v) {
        return new ArrayList<>(neighbors.get(v));
    }

    /**
     * Returns the great-circle distance between S and GOAL. Assumes
     * S and GOAL exist in this graph.
     */
    @Override
    public double estimatedDistanceToGoal(Long s, Long goal) {
        Node sNode = nodes.get(s);
        Node goalNode = nodes.get(goal);
        return greatCircleDistance(sNode.lon(), goalNode.lon(), sNode.lat(), goalNode.lat());
    }

    /** Returns a set of my vertices. Altering this set does not alter this graph. */
    public Set<Long> vertices() {
        return new HashSet<>(nodes.keySet());
    }

    /** Adds an edge to this graph if it doesn't already exist, using distance as the weight. */
    public void addWeightedEdge(long from, long to, String name) {
        if (nodes.containsKey(from) && nodes.containsKey(to)) {
            Node fromNode = nodes.get(from);
            Node toNode = nodes.get(to);
            double weight = greatCircleDistance(fromNode.lon(), toNode.lon(), fromNode.lat(), toNode.lat());
            neighbors.get(from).add(new WeightedEdge<>(from, to, weight, name));
        }
    }

    /** Adds an edge to this graph if it doesn't already exist. */
    public void addWeightedEdge(long from, long to, double weight, String name) {
        if (nodes.containsKey(from) && nodes.containsKey(to)) {
            neighbors.get(from).add(new WeightedEdge<>(from, to, weight, name));
        }
    }

    /** Adds an edge to this graph if it doesn't already exist. */
    public void addWeightedEdge(WeightedEdge<Long> edge) {
        if (nodes.containsKey(edge.from()) && nodes.containsKey(edge.to())) {
            neighbors.get(edge.from()).add(edge);
        }
    }

    /** Checks if a vertex has 0 out-degree from graph. */
    private boolean isNavigable(Node node) {
        return !neighbors.get(node.id()).isEmpty();
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    public double lat(long v) {
        if (!nodes.containsKey(v)) {
            return 0.0;
        }
        return nodes.get(v).lat();
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    public double lon(long v) {
        if (!nodes.containsKey(v)) {
            return 0.0;
        }
        return nodes.get(v).lon();
    }

    /** Adds a node to this graph, if it doesn't yet exist. */
    void addNode(Node node) {
        if (!nodes.containsKey(node.id())) {
            nodes.put(node.id(), node);
            neighbors.put(node.id(), new HashSet<>());
        }
    }

    Node getNode(long id) {
        return nodes.get(id);
    }

    Node.Builder nodeBuilder() {
        return new Node.Builder();
    }
}
