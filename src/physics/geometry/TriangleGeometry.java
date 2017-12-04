package physics.geometry;

import gizmo.GamePanel;
import lombok.Data;
import physics.interfaces.CollisionInterface;
import physics.interfaces.MotionInterface;
import physics.interfaces.OperateInterface;
import physics.interfaces.PrintInterface;
import physics.math.MathUtils;
import physics.math.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static gizmo.Constant.acceleration;
import static physics.math.MathUtils.polygonCollisionProcess;

@Data

public class TriangleGeometry extends Geometry implements PrintInterface, OperateInterface, MotionInterface, CollisionInterface {
    private PointGeometry point1;
    private PointGeometry point2;
    private PointGeometry point3;
    private int xpoints[];
    private int ypoints[];

    public TriangleGeometry(PointGeometry point1, PointGeometry point2, PointGeometry point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        xpoints = new int[3];
        ypoints = new int[3];
        reset();
    }

    public void reset() {
        xpoints[0] = (int) point1.x;
        xpoints[1] = (int) point2.x;
        xpoints[2] = (int) point3.x;
        ypoints[0] = (int) point1.y;
        ypoints[1] = (int) point2.y;
        ypoints[2] = (int) point3.y;
        pointGeometries = new ArrayList<>();
        pointGeometries.add(this.point1);
        pointGeometries.add(this.point2);
        pointGeometries.add(this.point3);
    }

    @Override
    public void move(double dx, double dy) {
        point1.x += dx;
        point1.y += dy;
        point2.x += dx;
        point2.y += dy;
        point3.x += dx;
        point3.y += dy;
        reset();
    }

    @Override
    public void delete() {

    }

    @Override
    public void rotate(double angle) {
        PointGeometry center = getCenter();
        MathUtils.rotatePoint(point1, center, angle);
        MathUtils.rotatePoint(point2, center, angle);
        MathUtils.rotatePoint(point3, center, angle);
        for (PointGeometry p : pointGeometries) {
            MathUtils.rotatePoint(p, center, angle);
        }
    }

    @Override
    public void zoom(double ratio) {
        PointGeometry center = getCenter();
        point1.x = (point1.x - center.x) * ratio + center.x;
        point1.y = (point1.y - center.y) * ratio + center.y;
        point2.x = (point2.x - center.x) * ratio + center.x;
        point2.y = (point2.y - center.y) * ratio + center.y;
        point3.x = (point3.x - center.x) * ratio + center.x;
        point3.y = (point3.y - center.y) * ratio + center.y;
        reset();
//        for (PointGeometry p : pointGeometries) {
//            p.x += (p.x-center.x)*ratio + center.x;
//            p.y += (p.y-center.y)*ratio + center.y;
//        }
    }

    @Override
    public boolean isInside(PointGeometry pointGeometry) {
        int i, j = 2;
        boolean oddNodes = false;

        for (i = 0; i < 3; i++) {
            if ((ypoints[i] < pointGeometry.y && ypoints[j] >= pointGeometry.y
                    || ypoints[j] < pointGeometry.y && ypoints[i] >= pointGeometry.y)
                    && (xpoints[i] <= pointGeometry.x || xpoints[j] <= pointGeometry.x)) {
                oddNodes ^= (xpoints[i] + (pointGeometry.y - ypoints[i]) / (ypoints[j] - ypoints[i]) * (xpoints[j] - xpoints[i]) < pointGeometry.x);
            }
            j = i;
        }

        return oddNodes;
    }

    @Override
    public PointGeometry getCenter() {
        return new PointGeometry((xpoints[0] + xpoints[1] + xpoints[2]) / 3,
                (ypoints[0] + ypoints[1] + ypoints[2]) / 3);
    }

    @Override
    public void print(Color color, Graphics g) {
        g.setColor(color);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    @Override
    public void reset(List<PointGeometry> pointGeometries) {
        if (pointGeometries.size() != 3) {
            this.pointGeometries = pointGeometries;
            return;
        }
        point1 = pointGeometries.get(0);
        point2 = pointGeometries.get(1);
        point3 = pointGeometries.get(2);
        reset();
    }

    @Override
    public void drawing(Color color, Graphics g) {
        g.setColor(color);
        for (int i = 0; i < pointGeometries.size() - 1; i++) {
            g.drawLine((int) pointGeometries.get(i).x,
                    (int) pointGeometries.get(i).y,
                    (int) pointGeometries.get(i + 1).x,
                    (int) pointGeometries.get(i + 1).y);
        }
    }

    @Override
    public TriangleGeometry clone() {
        TriangleGeometry newObject = (TriangleGeometry) super.clone();
        newObject.setPoint1((PointGeometry) getPoint1().clone());
        newObject.setPoint2((PointGeometry) getPoint2().clone());
        newObject.setPoint3((PointGeometry) getPoint3().clone());
        return newObject;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("TriangleGeometry[\n");
        sb.append("super:").append(super.toString());
        sb.append("point1:").append(point1.toString());
        sb.append("point2:").append(point2.toString());
        sb.append("point3:").append(point3.toString());
        sb.append("xpoints:").append("[");
        for (int i = 0; i < xpoints.length; i++) {
            sb.append(xpoints[i]).append(",");
        }
        sb.append("]\n");
        sb.append("ypoints:").append("[");
        for (int i = 0; i < ypoints.length; i++) {
            sb.append(ypoints[i]).append(",");
        }
        sb.append("]\n");

        sb.append("]\n");

        return sb.toString();
    }

    @Override
    public void update() {
        point1.x += velocity.getX() / GamePanel.FRAMES_PER_SECOND;
        point1.y += velocity.getY() / GamePanel.FRAMES_PER_SECOND;
        point2.x += velocity.getX() / GamePanel.FRAMES_PER_SECOND;
        point2.y += velocity.getY() / GamePanel.FRAMES_PER_SECOND;
        point3.x += velocity.getX() / GamePanel.FRAMES_PER_SECOND;
        point3.y += velocity.getY() / GamePanel.FRAMES_PER_SECOND;
        reset();
        for (PointGeometry p : pointGeometries) {
            p.x += velocity.getX() / GamePanel.FRAMES_PER_SECOND;
            p.y += velocity.getY() / GamePanel.FRAMES_PER_SECOND;
        }
    }

    @Override
    public void setRotationSpeed(PointGeometry rotateCenter, double speed) {

    }

    @Override
    public void setInstantaneousAcceleration(Vector acceleration) {
        velocity.plus(acceleration);
    }

    @Override
    public boolean onCollision(CircleGeometry ball) {
        Vector pro = polygonCollisionProcess(pointGeometries, ball, 2);
        if (pro == null) {
            return false;
        } else {
            setInstantaneousAcceleration(new Vector(pro, acceleration));
            return true;
        }
    }
}
