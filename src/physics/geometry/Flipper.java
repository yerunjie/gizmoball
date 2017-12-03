package physics.geometry;

import lombok.Data;
import physics.interfaces.FlipperOperateInterface;

@Data
public class Flipper extends RectangleGeometry implements FlipperOperateInterface {
    protected boolean isMoving;

    public Flipper(PointGeometry point1, PointGeometry point2) {
        super(point1, point2);
    }

    @Override
    public void moveFlipper(double dx, double dy) {
        move(dx, dy);
    }

    @Override
    public void rotateFlipper(double angle) {
        rotate(angle);
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
        return onCollision(ball, isMoving ? 3 : 2);
    }

}
