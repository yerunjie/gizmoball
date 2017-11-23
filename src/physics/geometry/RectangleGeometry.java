package physics.geometry;

import lombok.Data;

import java.awt.*;

@Data
public class RectangleGeometry extends TwoPointGeometry {
    protected double width;
    protected double height;

    public RectangleGeometry(PointGeometry point1, PointGeometry point2) {
        super(new PointGeometry(point1.x < point2.x ? point1.x : point2.x, point1.y < point2.y ? point1.y : point2.y),
                new PointGeometry(point1.x > point2.x ? point1.x : point2.x, point1.y > point2.y ? point1.y : point2.y));
        width = point2.x - point1.x;
        height = point2.y - point1.y;
    }

    @Override
    public boolean isInside(PointGeometry pointGeometry) {
        return pointGeometry.x > point1.x && pointGeometry.x < point2.x && pointGeometry.y > point1.y && pointGeometry.y < point2.y;
    }

    @Override
    public void print(Color color, Graphics g) {
        g.setColor(color);
        g.drawRect((int) point1.x, (int) point1.y, (int) width, (int) height);
    }
}
