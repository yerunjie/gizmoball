package physics.geometry;

import lombok.Data;

import java.awt.*;
import java.util.List;

@Data
public class PointGeometry extends Geometry {
    public static final PointGeometry origin = new PointGeometry(0, 0);
    protected double x;
    protected double y;

    public PointGeometry(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void reset(List<PointGeometry> pointGeometries) {
        if (pointGeometries.size() != 1) {
            throw new IllegalArgumentException();
        }
        x = pointGeometries.get(0).x;
        y = pointGeometries.get(0).y;
    }

    public PointGeometry(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public Point toPoint() {
        return new Point((int) x, (int) y);
    }
}
