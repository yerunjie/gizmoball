package physics.math;

import com.google.common.collect.Lists;
import physics.Vector;
import physics.geometry.LineGeometry;
import physics.geometry.PointGeometry;

import java.util.List;

public class MathUtils {
    public static final double APPROXIMATE_DISTANCE = 5;

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
        double l1=distanceBetweenTwoPoints(line.getPoint1(),line.getPoint2());
        double l2=distanceBetweenTwoPoints(line.getPoint1(),point);
        double l3=distanceBetweenTwoPoints(line.getPoint2(),point);
        if(Math.pow(l2,2)>=Math.pow(l1,2)+Math.pow(l3,2))
        {
            return l3;
        }
        else if(Math.pow(l3,2)>=Math.pow(l2,2)+Math.pow(l1,2)){
            return l2;
        }
        else {
            return Math.abs((a * x + b * y + c) / Math.sqrt(a * a + b * b));
        }
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

    public static boolean isInside(List<PointGeometry> pointGeometries, PointGeometry pointGeometry) {
        int i, j = pointGeometries.size() - 1;
        boolean oddNodes = false;
        double[] xpoints = pointGeometries.stream().mapToDouble(PointGeometry::getX).toArray();
        double[] ypoints = pointGeometries.stream().mapToDouble(PointGeometry::getY).toArray();
        for (i = 0; i < 3; i++) {
            if ((ypoints[i] < pointGeometry.getY() && ypoints[j] >= pointGeometry.getY()
                    || ypoints[j] < pointGeometry.getY() && ypoints[i] >= pointGeometry.getY())
                    && (xpoints[i] <= pointGeometry.getX() || xpoints[j] <= pointGeometry.getX())) {
                oddNodes ^= (xpoints[i] + (pointGeometry.getY() - ypoints[i]) / (ypoints[j] - ypoints[i]) * (xpoints[j] - xpoints[i]) < pointGeometry.getX());
            }
            j = i;
        }
        return oddNodes;
    }




}
