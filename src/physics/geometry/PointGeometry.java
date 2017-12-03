package physics.geometry;

import lombok.Data;
import physics.math.MathUtils;

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

    public PointGeometry(PointGeometry point) {
        this.x = point.x;
        this.y = point.y;
    }

    public Point toPoint() {
        return new Point((int) x, (int) y);
    }

    public boolean isCloseTo(PointGeometry another) {
        return Math.abs(x - another.x) < 0.001 && Math.abs(y - another.y) < 0.001;
    }

    @Override
    public PointGeometry clone() {
        return (PointGeometry) super.clone();
    }

    @Override
    public boolean onCollision(CircleGeometry ball) {
        return false;
    }

    @Override
    public String toString() {
        return "PointGeometry{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public PointGeometry getCenter() {
        return this;
    }
}
