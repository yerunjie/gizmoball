package physics.geometry;

import lombok.Data;

import java.awt.*;

@Data
public class SegmentGeometry extends LineGeometry {

    public SegmentGeometry(PointGeometry point1, PointGeometry point2) {
        super(point1, point2);
    }

    @Override
    public boolean isInside(PointGeometry pointGeometry) {
        throw new IllegalArgumentException();
    }

    @Override
    public void print(Color color, Graphics g) {
        g.setColor(color);
        g.drawLine((int) point1.x, (int) point1.y, (int) point2.x, (int) point2.y);
    }
}
