package physics.geometry;

import com.google.common.collect.Lists;
import gizmo.GamePanel;
import lombok.NoArgsConstructor;
import physics.interfaces.CollisionInterface;
import physics.interfaces.MotionInterface;
import physics.interfaces.OperateInterface;
import physics.interfaces.PrintInterface;
import physics.math.MathUtils;
import physics.math.Vector;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

import static gizmo.Constant.acceleration;
import static physics.math.MathUtils.polygonCollisionProcess;

@NoArgsConstructor
public class QuadrilateralGeometry extends Geometry implements PrintInterface, OperateInterface, MotionInterface, CollisionInterface ,Serializable {

    @Override
    public void reset(List<PointGeometry> pointGeometries) {
        this.pointGeometries = Lists.newArrayList();
        this.pointGeometries.addAll(pointGeometries);
    }

    @Override
    public PointGeometry getCenter() {
        return new PointGeometry(pointGeometries.stream().mapToDouble(point -> point.x).average().orElse(0),
                pointGeometries.stream().mapToDouble(point -> point.y).average().orElse(0));
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

    @Override
    public void update() {
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
    public void move(double dx, double dy) {
        for (PointGeometry pointGeometry : pointGeometries) {
            pointGeometry.x += dx;
            pointGeometry.y += dy;
        }
    }

    @Override
    public void delete() {

    }

    @Override
    public void rotate(double angle) {
        PointGeometry center = getCenter();
        for (PointGeometry p : pointGeometries) {
            MathUtils.rotatePoint(p, center, angle);
        }
    }

    @Override
    public void zoom(double ratio) {
        PointGeometry center = getCenter();
        for (PointGeometry pointGeometry : pointGeometries) {
            pointGeometry.x = (pointGeometry.x - center.x) * ratio + center.x;
            pointGeometry.y = (pointGeometry.y - center.y) * ratio + center.y;
        }
    }

    @Override
    public boolean isInside(PointGeometry pointGeometry) {
        return MathUtils.isInside(pointGeometries, pointGeometry);
    }

    @Override
    public void print(Color color, Graphics g) {
        g.setColor(color);
        g.fillPolygon(pointGeometries.stream().mapToInt(point -> (int) point.x).toArray(),
                pointGeometries.stream().mapToInt(point -> (int) point.y).toArray(), pointGeometries.size());

    }

    @Override
    public void drawing(Color color, Graphics g) {
        g.setColor(color);
        g.drawPolyline(pointGeometries.stream().mapToInt(point -> (int) point.x).toArray(),
                pointGeometries.stream().mapToInt(point -> (int) point.y).toArray(), pointGeometries.size());

    }
}
