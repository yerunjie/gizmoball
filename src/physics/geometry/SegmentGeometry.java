package physics.geometry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import physics.interfaces.CollisionInterface;
import physics.interfaces.PrintInterface;

import java.awt.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class SegmentGeometry extends LineGeometry implements PrintInterface {

    public SegmentGeometry(PointGeometry point1, PointGeometry point2) {
        super(point1, point2);
    }

    @Override
    public void print(Color color, Graphics g) {
        g.setColor(color);
        g.drawLine((int) point1.x, (int) point1.y, (int) point2.x, (int) point2.y);
    }

}
