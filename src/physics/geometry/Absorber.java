package physics.geometry;

import gizmo.PlayRoom;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.Serializable;

@NoArgsConstructor
public class Absorber extends RectangleGeometry implements Serializable {
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
        Color color1 = new Color(255,165,79);
        super.print(color1, g);
    }
}
