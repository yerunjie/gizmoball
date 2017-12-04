package physics.math;

import com.google.common.collect.Lists;
import physics.geometry.CircleGeometry;
import physics.geometry.LineGeometry;
import physics.geometry.PointGeometry;

import java.util.List;

import static gizmo.Constant.APPROXIMATE_DISTANCE;

public class MathUtils {

    public static double distanceBetweenTwoPoints(PointGeometry p1, PointGeometry p2) {
        double dx2 = Math.pow(p1.getX() - p2.getX(), 2);
        double dy2 = Math.pow(p1.getY() - p2.getY(), 2);
        return Math.sqrt(dx2 + dy2);
    }

    public static boolean isApproximateEqual(PointGeometry p1, PointGeometry p2) {
        return distanceBetweenTwoPoints(p1, p2) < APPROXIMATE_DISTANCE;
    }

    /**
     * 计算球心与线段距离
     * 1.边 2.point1 3.point2
     */

    public static PointToLine calculatePointToLineDistance(PointGeometry point, LineGeometry line) {
        double x = point.getX();
        double y = point.getY();
        double a = line.getA();
        double b = line.getB();
        double c = line.getC();
        double l1 = distanceBetweenTwoPoints(line.getPoint1(), line.getPoint2());
        double l2 = distanceBetweenTwoPoints(line.getPoint1(), point);
        double l3 = distanceBetweenTwoPoints(line.getPoint2(), point);
        if (Math.pow(l2, 2) >= Math.pow(l1, 2) + Math.pow(l3, 2)) {
            return new PointToLine(l3, 3);
        } else if (Math.pow(l3, 2) >= Math.pow(l2, 2) + Math.pow(l1, 2)) {
            return new PointToLine(l2, 2);
        } else {
            double distance = Math.abs((a * x + b * y + c) / Math.sqrt(a * a + b * b));
            return new PointToLine(distance, 1);
        }
    }

    public static void rotatePoint(PointGeometry p, PointGeometry center, double angle) {
        angle = angle / 180.0 * Math.PI;
        double tmpx = p.getX();
        double tmpy = p.getY();
        p.setX((tmpx - center.getX()) * Math.cos(angle) - (tmpy - center.getY()) * Math.sin(angle) + center.getX());
        p.setY((tmpx - center.getX()) * Math.sin(angle) + (tmpy - center.getY()) * Math.cos(angle) + center.getY());
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
        for (i = 0; i < pointGeometries.size(); i++) {
            if ((ypoints[i] < pointGeometry.getY() && ypoints[j] >= pointGeometry.getY()
                    || ypoints[j] < pointGeometry.getY() && ypoints[i] >= pointGeometry.getY())
                    && (xpoints[i] <= pointGeometry.getX() || xpoints[j] <= pointGeometry.getX())) {
                oddNodes ^= (xpoints[i] + (pointGeometry.getY() - ypoints[i]) / (ypoints[j] - ypoints[i]) * (xpoints[j] - xpoints[i]) < pointGeometry.getX());
            }
            j = i;
        }
        return oddNodes;
    }

    public static Vector lineCollisionProcess(int collisionPoint, CircleGeometry ball, PointGeometry p1, PointGeometry p2) {
        return lineCollisionProcess(collisionPoint, ball, p1, p2, 2);
    }

    public static Vector lineCollisionProcess(int collisionPoint, CircleGeometry ball, PointGeometry p1, PointGeometry p2, int scalar) {
        Vector pro;
        if (collisionPoint == 1) {
            pro = ball.getVelocity().takeFrom(ball.getVelocity().projection(new LineGeometry(p1, p2)));
            ball.setInstantaneousAcceleration(pro.negate().multiplyScalar(scalar)
            );
        } else if (collisionPoint == 2) {
            pro = ball.getVelocity().projection(new LineGeometry(ball.getCenter(), p1));
            ball.setInstantaneousAcceleration(pro.negate().multiplyScalar(scalar));
        } else {
            pro = ball.getVelocity().projection(new LineGeometry(ball.getCenter(), p2));
            ball.setInstantaneousAcceleration(pro.negate().multiplyScalar(scalar));
        }
        return pro;
    }

    public static Vector polygonCollisionProcess(List<PointGeometry> pointGeometries, CircleGeometry ball, int scalar) {
        Vector pro = null;
        for (int i = 0; i < pointGeometries.size() - 1; i++) {
            LineGeometry lineGeometry = new LineGeometry(pointGeometries.get(i), pointGeometries.get(i + 1));
            if (MathUtils.calculatePointToLineDistance(ball.getCenter(), lineGeometry).getDistance() <= ball.getR()) {
                pro = MathUtils.lineCollisionProcess(
                        MathUtils.calculatePointToLineDistance(ball.getCenter(), lineGeometry).getCollisionPoint(),
                        ball, pointGeometries.get(i), pointGeometries.get(i + 1), scalar);
                return pro;
            }
        }
        LineGeometry lineGeometry = new LineGeometry(pointGeometries.get(0), pointGeometries.get(pointGeometries.size() - 1));
        if (MathUtils.calculatePointToLineDistance(ball.getCenter(), lineGeometry).getDistance() <= ball.getR()) {
            pro = MathUtils.lineCollisionProcess(
                    MathUtils.calculatePointToLineDistance(ball.getCenter(), lineGeometry).getCollisionPoint(),
                    ball, pointGeometries.get(0), pointGeometries.get(pointGeometries.size() - 1), scalar);
        }
        return pro;
    }
}
