package physics.math;

import com.google.common.collect.Lists;
import physics.geometry.LineGeometry;
import physics.geometry.PointGeometry;

import java.util.List;

public class MathUtils {
    public static final double APPROXIMATE_DISTANCE = 1;

    public static double distanceBetweenTwoPoints(PointGeometry p1, PointGeometry p2) {
        double dx2 = Math.pow(p1.getX() - p2.getX(), 2);
        double dy2 = Math.pow(p1.getY() - p2.getY(), 2);
        return Math.sqrt(dx2 + dy2);
    }

    public static boolean isApproximateEqual(PointGeometry p1, PointGeometry p2) {
        return distanceBetweenTwoPoints(p1, p2) < APPROXIMATE_DISTANCE;
    }

    public static double calculatePointToLineDistance(PointGeometry point, LineGeometry line) {
        double x = point.getX();
        double y = point.getY();
        double a = line.getA();
        double b = line.getB();
        double c = line.getC();
        return Math.abs((a * x + b * y + c) / Math.sqrt(a * a + b * b));
    }

    public static List<Double> binaryLinearEquationGroup(double a1, double b1, double c1,
                                                         double a2, double b2, double c2) {
        c1 = -c1;
        c2 = -c2;
        double x, y;
        double j, k, l;
        j = a2 / a1;
        a1 = a1 * j;
        b1 = b1 * j;
        c1 = c1 * j;
        k = c2 - c1;
        l = b2 - b1;
        y = k / l;
        x = (c2 - b2 * y) / a2;
        return Lists.newArrayList(x, y);
    }
}
