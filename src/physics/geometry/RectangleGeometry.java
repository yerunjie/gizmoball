package physics.geometry;

import com.google.common.collect.Lists;
import lombok.Data;
import physics.interfaces.OperateInterface;
import physics.interfaces.PrintInterface;
import physics.math.MathUtils;

import java.awt.*;
import java.util.List;

@Data
public class RectangleGeometry extends TwoPointGeometry implements PrintInterface, OperateInterface {
    protected double width;
    protected double height;

    public RectangleGeometry(PointGeometry point1, PointGeometry point2) {
        super(new PointGeometry(point1.x < point2.x ? point1.x : point2.x, point1.y < point2.y ? point1.y : point2.y),
                new PointGeometry(point1.x > point2.x ? point1.x : point2.x, point1.y > point2.y ? point1.y : point2.y));
        resetWidthAndHeight();
    }

    private void resetWidthAndHeight() {
        width = point2.x - point1.x;
        height = point2.y - point1.y;
        temp = Lists.newArrayList();
        temp.add(point1);
        temp.add(new PointGeometry(point1.x > point2.x ? point1.x : point2.x, point1.y < point2.y ? point1.y : point2.y));
        temp.add(point2);
        temp.add(new PointGeometry(point1.x < point2.x ? point1.x : point2.x, point1.y > point2.y ? point1.y : point2.y));
    }

    @Override
    public void reset(List<PointGeometry> pointGeometries) {
        if (pointGeometries.size() == 2) {
            super.reset(pointGeometries);
            reset();
        } else if (pointGeometries.size() == 4) {
            temp = pointGeometries;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void reset() {
        PointGeometry point1 = this.point1;
        PointGeometry point2 = this.point2;
        this.point1 = new PointGeometry(point1.x < point2.x ? point1.x : point2.x, point1.y < point2.y ? point1.y : point2.y);
        this.point2 = new PointGeometry(point1.x > point2.x ? point1.x : point2.x, point1.y > point2.y ? point1.y : point2.y);
        resetWidthAndHeight();
    }

    @Override
    public void move(double dx, double dy) {

    }

    @Override
    public void delete() {

    }

    @Override
    public void rotate(double angle) {

    }

    @Override
    public void zoom(double ratio) {

    }

    @Override
    public boolean isInside(PointGeometry pointGeometry) {
        return MathUtils.isInside(temp, pointGeometry);
    }

    @Override
    public void print(Color color, Graphics g) {
        g.setColor(color);
        g.drawPolygon(temp.stream().mapToInt(point -> (int) point.x).toArray(),
                temp.stream().mapToInt(point -> (int) point.y).toArray(), temp.size());
    }

    @Override
    public void drawing(Color color, Graphics g) {
        g.setColor(color);
        g.drawRect((int) point1.x, (int) point1.y, (int) width, (int) height);
    }
}
