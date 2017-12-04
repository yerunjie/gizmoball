package physics.geometry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import physics.interfaces.CollisionInterface;
import physics.math.MathUtils;
import physics.math.Vector;

@EqualsAndHashCode(callSuper = true)
@Data
public class SegmentGeometry extends LineGeometry implements CollisionInterface {

    private boolean isTopBottom;

    public SegmentGeometry(PointGeometry point1, PointGeometry point2, boolean isTopBottom) {
        super(point1, point2);
        this.isTopBottom = isTopBottom;
    }


    @Override
    public PointGeometry getCenter() {
        return new PointGeometry((point1.x + point2.x) / 2.0, (point1.y + point2.y) / 2.0);
    }

    @Override
    public SegmentGeometry clone() {
        return (SegmentGeometry) super.clone();
    }

    @Override
    public boolean onCollision(CircleGeometry ball) {
        if (MathUtils.calculatePointToLineDistance(ball.getCenter(), this).getDistance() <= ball.getR()) {
            if (isTopBottom) {
                ball.setInstantaneousAcceleration(new Vector(0, -ball.getVelocity().getY() * 2));
            } else {
                ball.setInstantaneousAcceleration(new Vector(-ball.getVelocity().getX() * 2, 0));
            }
            return true;
        }
        return false;
    }


}
