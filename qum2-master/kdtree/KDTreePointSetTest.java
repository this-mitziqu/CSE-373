package kdtree;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreePointSetTest {
    @Test
    public void buildTreeTest() {

        Random random = new Random(300);

        for (int i = 0; i <= 10000; i++) {

            Point p1 = new Point(random.nextDouble(), random.nextDouble()); // Point with x = 1.1, y = 2.2
            Point p2 = new Point(random.nextDouble(), random.nextDouble());
            Point p3 = new Point(random.nextDouble(), random.nextDouble());
            Point p4 = new Point(random.nextDouble(), random.nextDouble());
            Point p5 = new Point(random.nextDouble(), random.nextDouble());
            Point p6 = new Point(random.nextDouble(), random.nextDouble());
            System.out.println(p1.toString());
            System.out.println(p2.toString());
            System.out.println(p3.toString());
            System.out.println(p4.toString());
            System.out.println(p5.toString());
            System.out.println(p6.toString());

            KDTreePointSet kd = new KDTreePointSet(List.of(p1, p2, p3, p4, p5, p6));
            PointSet na = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6));
            double targetX = random.nextDouble();
            double targetY = random.nextDouble();
            System.out.println(targetX + ", " + targetY);
            System.out.println(i);
            assertEquals(na.nearest(targetX, targetY), kd.nearest(targetX, targetY));
        }

    }

}
