package physics.geometry;

import gizmo.PlayRoom;

public class Absorber extends RectangleGeometry {
    public Absorber(PointGeometry point1, PointGeometry point2) {
        super(point1, point2);
    }

    @Override
    public Absorber clone() {
        return (Absorber) super.clone();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Absorber[\n");
        sb.append("super:").append(super.toString());
        sb.append("]\n");

        return sb.toString();
    }

    @Override
    public boolean onCollision(CircleGeometry ball) {
        if (super.onCollision(ball)) {
            PlayRoom.playRoom.endGame();
        }
        return false;
    }
}
