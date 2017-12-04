package physics.geometry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import physics.interfaces.FlipperOperateInterface;
import physics.math.Vector;

import static gizmo.GamePanel.segmentGeometries;

@Data
@EqualsAndHashCode(callSuper = true)
public class Flipper extends RectangleGeometry implements FlipperOperateInterface {
    protected boolean isMoving;

    public Flipper(PointGeometry point1, PointGeometry point2) {
        super(point1, point2);
    }

    @Override
    public void moveFlipper(double dx, double dy) {
        move(dx, dy);
        for (SegmentGeometry segmentGeometry : segmentGeometries) {
            if (segmentGeometry.onCollisionObstacle(this)) {
                move(-dx, -dy);
                return;
            }
        }
    }

    @Override
    public void rotateFlipper(double angle) {
        rotate(angle);
        for (SegmentGeometry segmentGeometry : segmentGeometries) {
            if (onCollisionObstacle(segmentGeometry)) {
                rotate(-angle);
                return;
            }
        }
    }

    @Override
    public Flipper clone() {
        return (Flipper) super.clone();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Flipper[\n");
        sb.append("super:").append(super.toString());
        sb.append("]\n");

        return sb.toString();
    }

    @Override
    public boolean onCollision(CircleGeometry ball) {
        if (onCollision(ball, isMoving ? 2 : 2)) {
            setVelocity(new Vector(Vector.ZERO));
            return true;
        }
        return false;
    }

}
