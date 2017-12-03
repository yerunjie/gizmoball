package physics.geometry;

import gizmo.GamePanel;
import physics.Vector;

public class Ball extends CircleGeometry {
    public Ball(PointGeometry point1, PointGeometry point2) {
        super(point1, point2);
    }

    @Override
    public Ball clone() {
        return (Ball) super.clone();
    }

    @Override
    public void updateVelocity() {//不收阻力
        velocity.plus(new Vector(constantAcceleration).multiplyScalar(1.0 / GamePanel.FRAMES_PER_SECOND));
    }
}
