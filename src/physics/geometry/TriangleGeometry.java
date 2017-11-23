package physics.geometry;

import lombok.Data;

import java.awt.*;

@Data
public class TriangleGeometry extends Geometry {
    private PointGeometry point1;
    private PointGeometry point2;
    private PointGeometry point3;
    private int xpoints[];
    private int ypoints[];

    public TriangleGeometry(PointGeometry point1, PointGeometry point2, PointGeometry point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        xpoints = new int[3];
        ypoints = new int[3];
        reset();
    }

    public void reset() {
        xpoints[0] = (int) point1.x;
        xpoints[1] = (int) point2.x;
        xpoints[2] = (int) point3.x;
        ypoints[0] = (int) point1.y;
        ypoints[1] = (int) point2.y;
        ypoints[2] = (int) point3.y;
    }

    @Override
    public boolean isInside(PointGeometry pointGeometry) {
        return false;
    }

    @Override
    public void print(Color color, Graphics g) {
        g.setColor(color);
        g.drawPolygon(xpoints, ypoints, 3);
    }
}
