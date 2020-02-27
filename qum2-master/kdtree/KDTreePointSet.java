package kdtree;

import java.util.List;

import static kdtree.Point.distanceSquaredBetween;

public class KDTreePointSet implements PointSet {
    private static final boolean HORIZONTAL = true;
    private Node root;
    public class Node {
        Point p;
        boolean orientation;
        Node left;
        Node right;

        Node(Point p, boolean orientation){
            this.p = p;
            this.orientation = orientation;
            left = null;
            right = null;
        }
    }
    private List<Point> points;

    /**
     * Instantiates a new KDTree with the given points.
     * @param points a non-null, non-empty list of points to include
     *               (makes a defensive copy of points, so changes to the list
     *               after construction don't affect the point set)
     */
    public KDTreePointSet(List<Point> points) {

        for (Point point : points){
            add(point);
        }


        //create tree in constructor
    }
    private void add(Point p) {
        root = addRecursive(root, p, HORIZONTAL);
    }

    private Node addRecursive(Node n, Point p, boolean orien){
        if (n == null){
            return new Node(p, orien);
        }
        if (orien == HORIZONTAL) {
            if (p.x() < n.p.x()) {
                n.left = addRecursive(n.left, p, !orien);
            } else  {
                n.right = addRecursive(n.right, p, !orien);
            }
        } else {
            if (p.y() < n.p.y()) {
                n.left = addRecursive(n.left, p, !orien);
            } else  {
                n.right = addRecursive(n.right, p, !orien);
            }
        }
        return n;
    }

    /**
     * Returns the point in this set closest to (x, y) in (usually) O(log N) time,
     * where N is the number of points in this set.
     */
    @Override
    public Point nearest(double x, double y) {
        Point nearestPoint = new Point(root.p.x(), root.p.y());
        return nearest(x, y, root, nearestPoint);
    }

    private Point nearest(double x, double y, Node curr, Point nearestPoint) {
        if (curr == null) {
            return nearestPoint;
        }
        if (curr.p.distanceSquaredTo(x, y) < nearestPoint.distanceSquaredTo(x, y)) {
            nearestPoint = curr.p;
        }

        //pruning
        Node goodSide;
        Node badSide;
        if ((curr.orientation == HORIZONTAL && x < curr.p.x()) ||
               (curr.orientation != HORIZONTAL && y < curr.p.y())) {
            goodSide = curr.left;
            badSide = curr.right;
        } else {
            goodSide = curr.right;
            badSide = curr.left;
       }
        nearestPoint = nearest(x, y, goodSide, nearestPoint);
       if ((curr.orientation == HORIZONTAL && (nearestPoint.distanceSquaredTo(x, y) >
               distanceSquaredBetween(curr.p.x(), x, y, y)))||
        (curr.orientation != HORIZONTAL && nearestPoint.distanceSquaredTo(x, y) >
                distanceSquaredBetween(x, x, curr.p.y(), y))) {//bad side could still have something useful
            nearestPoint = nearest(x, y, badSide, nearestPoint);
       }
        return nearestPoint;
    }






}
