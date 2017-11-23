package physics.geometry;

import lombok.Data;

import java.awt.*;

@Data
public class PointGeometry extends Geometry {
    protected double x;
    protected double y;

    public PointGeometry(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isInside(PointGeometry pointGeometry) {
        throw new IllegalArgumentException();
    }

    @Override
    public void print(Color color,Graphics g) {

    }
}
