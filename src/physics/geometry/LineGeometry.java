package physics.geometry;

import lombok.Data;

import java.util.List;

import static physics.math.MathUtils.binaryLinearEquationGroup;

@Data
public class LineGeometry extends TwoPointGeometry {
    protected double a;
    protected double b;
    protected double c;

    public LineGeometry(PointGeometry point1, PointGeometry point2) {
        super(point1, point2);
        c = 1;
        List<Double> list = binaryLinearEquationGroup(point1.x, point1.y, 1,
                point2.x, point2.y, 1);
        a = list.get(0);
        b = list.get(1);
    }

    @Override
    public boolean onCollision(CircleGeometry ball) {
        return false;
    }

    @Override
    public PointGeometry getCenter() {
        return null;
    }

    @Override
    public LineGeometry clone() {
        return (LineGeometry) super.clone();
    }
}
