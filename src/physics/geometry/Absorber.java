package physics.geometry;

import gizmo.PlayRoom;

import java.awt.*;

public class Absorber extends RectangleGeometry {
    public Absorber(PointGeometry point1, PointGeometry point2) {
        super(point1, point2);
    }

    @Override
    public Absorber clone() {
        return (Absorber) super.clone();
    }

    @Override
    public boolean onCollision(CircleGeometry ball) {
        if (super.onCollision(ball)) {
            PlayRoom.playRoom.endGame();
        }
        return false;
    }

    @Override
    public void print(Color color, Graphics g) {
        super.print(Color.YELLOW, g);
    }
}
