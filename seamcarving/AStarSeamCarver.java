package seamcarving;

import astar.AStarGraph;
import astar.AStarSolver;
import astar.WeightedEdge;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AStarSeamCarver implements SeamCarver {
    private Picture picture;
    private AStarGraphSeamHorizontal seamH;
    private AStarGraphSeamVertical seamV;
    private AStarSolver<Point> starH;
    private AStarSolver<Point> starV;
    private List<Point> solutionH;
    private List<Point> solutionV;

    public AStarSeamCarver(Picture picture) {
        if (picture == null) {
            throw new NullPointerException("Picture cannot be null.");
        }
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public Color get(int x, int y) {
        return picture.get(x, y);
    }

    public double energy(int x, int y) {
        double rX;
        double gX;
        double bX;
        double rY;
        double gY;
        double bY;
        if (x < 0 || x >= width() || y < 0 || y >= height()){
            return 0;
        }
        //*******
        int xL = x -1;
        int xR = x +1;
        int yT = y - 1;
        int yB = y + 1;

        if (xL < 0){
            xL = width()-1;
        }
        if (xR > width() - 1){
            xR = 0;
        }
        if (yT < 0){
            yT = height() - 1;
        }
        if (yB > height() -1){
            yB = 0;
        }
        rX = get(xL, y).getRed() - get(xR, y).getRed();
        gX = get(xL, y).getGreen() - get(xR, y).getGreen();
        bX = get(xL, y).getBlue() - get(xR, y).getBlue();
        double xGradientSquare = Math.pow(rX, 2) + Math.pow(gX, 2) + Math.pow(bX, 2);
        rY = get(x, yT).getRed() - get(x, yB).getRed();
        gY = get(x, yT).getGreen() - get(x, yB).getGreen();
        bY = get(x, yT).getBlue() - get(x, yB).getBlue();
        double yGradientSquare = Math.pow(rY, 2) + Math.pow(gY, 2) + Math.pow(bY, 2);
        return Math.sqrt(xGradientSquare + yGradientSquare);
    }

    // with minimum total energy
    // Each edge weight is based on a vertex (pixel) rather than the edge itself.
    // to find the shortest path from any of the WW pixels in the top row to any of the WW pixels in the bottom row.
    public int[] findHorizontalSeam() {
        int i = 0;
        seamH = new AStarGraphSeamHorizontal();
        starH = new AStarSolver<>(seamH, seamH.start, seamH.end, 1000000000);
        solutionH = starH.solution();
        solutionH.remove(0);
        solutionH.remove(solutionH.size()-1);
        int[] result = new int[picture.width()];
        for (Point p: solutionH){
            result[i] = p.getY();
            i++;
        }
        return result;
    }

    //dummy node 掐头去尾
    public int[] findVerticalSeam() {
        int i = 0;
        seamV = new AStarGraphSeamVertical();
        starV = new AStarSolver<>(seamV, seamV.start, seamV.end, 1000000000);
        solutionV = starV.solution();
        solutionV.remove(0);
        solutionV.remove(solutionV.size()-1);
        int[] result = new int[picture.height()];
        for (Point p: solutionV){
            result[i] = p.getX();
            i++;
        }
        return result;
    }

    public class Point {
        private int x;
        private int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {return true; }
            if (!(o instanceof Point)) {return false; }
            Point point = (Point) o;
            return getX() == point.getX() &&
                    getY() == point.getY();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getX(), getY());
        }
    }

    //only declare generic once
    private class AStarGraphSeamHorizontal implements AStarGraph<Point> {
        private Point start;
        private Point end;

        public AStarGraphSeamHorizontal(){
            start = new Point(-1, -1);
            end = new Point(-2, -2);
        }

        @Override
        public List<WeightedEdge<Point>> neighbors(Point p) {
            List<WeightedEdge<Point>> neighbors = new ArrayList<>();
            Point p1;
            Point p2;
            Point p3;
            // width & height 搞反
            if (p.equals(start)){
                for (int i = 0; i < height(); i++){
                    neighbors.add(new WeightedEdge(p, new Point(0, i), energy(0, i)));
                }
            } else if (p.getX() == width() - 1){
                neighbors.add(new WeightedEdge(p, end, 0));
            } else {
                if (p.getY()-1 >= 0) {
                    p1 = new Point(p.getX() + 1, p.getY() - 1);
                    neighbors.add(new WeightedEdge(p, p1, energy(p1.getX(), p1.getY())));
                }
                p2 = new Point(p.getX() + 1, p.getY());
                neighbors.add(new WeightedEdge(p, p2, energy(p2.getX(), p2.getY())));
                //是 "<" 不是 "<="
                if (p.getY() +1 < height()) {
                    p3 = new Point(p.getX() + 1, p.getY() + 1);
                    neighbors.add(new WeightedEdge(p, p3, energy(p3.getX(), p3.getY())));
                }
                //on the boarder
                //width and height
            }
            return neighbors;
        }
        @Override
        public double estimatedDistanceToGoal(Point s, Point goal) {
            return 0;
        }
    }

    private class AStarGraphSeamVertical implements AStarGraph<Point> {
        private Point start;
        private Point end;

        public AStarGraphSeamVertical(){
            start = new Point(-1, -1);
            end = new Point(-2, -2);
        }

        @Override
        public List<WeightedEdge<Point>> neighbors(Point p) {
            List<WeightedEdge<Point>> neighbors = new ArrayList<>();
            Point p1;
            Point p2;
            Point p3;
            //System.out.println("For this point: (" + p.getX() + ", " + p.getY() + ")...");
            if (p.equals(start)){
                for (int i = 0; i < width(); i++){
                    neighbors.add(new WeightedEdge(p, new Point(i, 0), energy(i, 0)));
                }
            } else if (p.getY() == height() - 1){
                neighbors.add(new WeightedEdge(p, end, 0));
            } else {
                if (p.getX()-1 >= 0) {
                    p1 = new Point(p.getX() - 1, p.getY() + 1);
                    neighbors.add(new WeightedEdge(p, p1, energy(p1.getX(), p1.getY())));
                }
                p2 = new Point(p.getX(), p.getY()+1);
                neighbors.add(new WeightedEdge(p, p2, energy(p2.getX(), p2.getY())));
                if (p.getX() +1 < width()) {
                    p3 = new Point(p.getX() + 1, p.getY() + 1);
                    neighbors.add(new WeightedEdge(p, p3, energy(p3.getX(), p3.getY())));
                }
                //on the boarder
                //width and height
            }
            //for (WeightedEdge<Point> we : neighbors) {
            //System.out.println("      Added (" + we.to().getX() + ", " + we.to().getY() + ")");
            return neighbors;
        }
        @Override
        public double estimatedDistanceToGoal(Point s, Point goal) {
            return 0;
        }
    }
}
