package physics.geometry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import physics.interfaces.FlipperOperateInterface;
import physics.math.Vector;

import java.awt.*;
import java.io.Serializable;

import static gizmo.GamePanel.segmentGeometries;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Flipper extends RectangleGeometry implements FlipperOperateInterface, Serializable {
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
    public boolean onCollision(CircleGeometry ball) {
        if (onCollision(ball, isMoving ? 2 : 2)) {
            setVelocity(new Vector(Vector.ZERO));
            return true;
        }
        return false;
    }

    @Override
    public void print(Color color, Graphics g) {
        Color color1 = new Color(176,196,222);
        super.print(color1, g);
    }

}
