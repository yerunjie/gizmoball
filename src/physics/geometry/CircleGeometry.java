package physics.geometry;

import lombok.Data;
import physics.interfaces.CollisionInterface;
import physics.interfaces.OperateInterface;
import physics.interfaces.PrintInterface;

import java.awt.*;

import static physics.math.MathUtils.distanceBetweenTwoPoints;

@Data
public class CircleGeometry extends RectangleGeometry implements PrintInterface, OperateInterface {
    protected double r;
    protected PointGeometry center;

    /*public CircleGeometry(double r, PointGeometry center) {
        this.r = r;
        this.center = center;
    }*/

    public CircleGeometry(PointGeometry point1, PointGeometry point2) {
        super(point1, point2);
        reset();
    }

    @Override
    public void reset() {
        super.reset();
        r = width > height ? height / 2 : width / 2;
        center = new PointGeometry(point1.x + r, point1.y + r);
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

    @Override
    public void drawing(Color color, Graphics g) {
        print(color, g);
    }
}
