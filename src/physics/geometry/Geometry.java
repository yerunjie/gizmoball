package physics.geometry;

import com.google.common.collect.Lists;
import gizmo.Constant;
import gizmo.GamePanel;
import lombok.Data;
import physics.interfaces.CollisionInterface;
import physics.interfaces.MotionInterface;
import physics.math.MathUtils;
import physics.math.Vector;

import java.awt.*;
import java.util.IntSummaryStatistics;
import java.util.List;

import static gizmo.Constant.centerPointRadius;


@Data
public abstract class Geometry implements Cloneable {
    public static int FRAMES_PER_SECOND = 100;
    private static int count = 0;
    protected Vector constantAcceleration;
    protected double frictionCoefficient;
    protected Vector velocity;
    protected int id;
    protected List<PointGeometry> pointGeometries;
    protected Geometry() {
        id = generateId();
        pointGeometries = Lists.newArrayList();
        frictionCoefficient = Constant.frictionCoefficient;
        constantAcceleration = new Vector(0, 0);
        velocity = new Vector(0, 0);
    }

    protected synchronized static int generateId() {
        count++;
        return count;
    }

    public abstract void reset(List<PointGeometry> pointGeometries);


    public abstract PointGeometry getCenter();

    public void printEditBound(Graphics g) {
        IntSummaryStatistics xStatistics = pointGeometries.stream().mapToInt(point -> (int) point.x).summaryStatistics();
        IntSummaryStatistics yStatistics = pointGeometries.stream().mapToInt(point -> (int) point.y).summaryStatistics();
        g.setColor(Color.cyan);
        g.drawRect(xStatistics.getMin(), yStatistics.getMin(), xStatistics.getMax() - xStatistics.getMin(), yStatistics.getMax() - yStatistics.getMin());
        PointGeometry center = getCenter();
        g.drawOval((int) center.x - centerPointRadius, (int) center.y - centerPointRadius, 2 * centerPointRadius, 2 * centerPointRadius);
    }

    @Override
    public Geometry clone() {
        Geometry newObject = null;
        try {
            newObject = (Geometry) super.clone();
            newObject.setConstantAcceleration((Vector) getConstantAcceleration().clone());
            newObject.setVelocity((Vector) getVelocity().clone());
            List<PointGeometry> newTemp = Lists.newArrayList();
            for (PointGeometry pointGeometry : pointGeometries) {
                newTemp.add(pointGeometry.clone());
            }
            newObject.setPointGeometries(newTemp);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return newObject;
    }

    public void updateVelocity() {
        velocity.plus(new Vector(velocity).negate().setNorm(frictionCoefficient).multiplyScalar(1.0 / GamePanel.FRAMES_PER_SECOND));
        velocity.plus(new Vector(constantAcceleration).multiplyScalar(1.0 / GamePanel.FRAMES_PER_SECOND));
    }

    public abstract boolean onCollision(CircleGeometry ball);

    public boolean onCollisionObstacle(CollisionInterface obstacle) {
        boolean result = false;
        if (obstacle instanceof CircleGeometry) {
            result = onCollision((CircleGeometry) obstacle);
        } else if (this instanceof CircleGeometry) {
            result = obstacle.onCollision((CircleGeometry) this);
        } else {
            if (obstacle instanceof SegmentGeometry) {
                if (this instanceof SegmentGeometry) {
                    return false;
                } else {
                    for (PointGeometry pointGeometry : pointGeometries) {
                        if (MathUtils.calculatePointToLineDistance(pointGeometry, (SegmentGeometry) obstacle).getDistance() < (this instanceof Flipper ? 2 : 2)) {
                            result = true;
                            break;
                        }
                    }
                }
            } else {
                List<PointGeometry> list = ((Geometry) obstacle).pointGeometries;
                for (PointGeometry pointGeometry : list) {
                    if (MathUtils.isInside(pointGeometries, pointGeometry)) {
                        result = true;
                        break;
                    }
                }
                for (PointGeometry pointGeometry : pointGeometries) {
                    if (MathUtils.isInside(list, pointGeometry)) {
                        result = true;
                        break;
                    }
                }
            }
        }
        if (result) {
            if (obstacle instanceof MotionInterface) {
                ((MotionInterface) obstacle).reUpdate();
            }
            if (this instanceof MotionInterface) {
                ((MotionInterface) this).reUpdate();
            }
            setVelocity(new Vector(Vector.ZERO));
            ((Geometry) obstacle).setVelocity(new Vector(Vector.ZERO));
        }
        return result;
    }
}
