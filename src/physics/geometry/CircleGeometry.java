package physics.geometry;

import lombok.Data;

import java.awt.*;
import java.util.List;

import static physics.math.MathUtils.binaryLinearEquationGroup;
import static physics.math.MathUtils.distanceBetweenTwoPoints;

@Data
public class CircleGeometry extends Geometry {
    protected double r;
    protected PointGeometry center;

    public CircleGeometry(double r, PointGeometry center) {
        this.r = r;
        this.center = center;
    }

    @Override
    public boolean isInside(PointGeometry pointGeometry) {
        return r > distanceBetweenTwoPoints(center, pointGeometry);
    }

    @Override
    public void print(Color color, Graphics g) {
        g.setColor(color);
        g.drawOval((int) (center.x - r), (int) (center.y - r), 2 * (int) r, 2 * (int) r);
    }
}
