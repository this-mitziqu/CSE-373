package kdtree;
import org.junit.Test;

import java.util.List;


public class NaivePointSetTest {
    @Test
    public void nearestTest() {
        Point p1 = new Point(1.1, 2.2); // Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);


        PointSet na = new NaivePointSet(List.of(p1, p2, p3));
        double x = 3.0;
        double y = 4.0;           // Mouse-click at (3, 4)
        Point ret = na.nearest(x, y);   // ret == p2
        double getX = ret.x();                     // Evaluates to 3.3
        double getY = ret.y();                      // Evaluates to 4.4
        System.out.println("" + getX + ", " + getY);
    }
}
