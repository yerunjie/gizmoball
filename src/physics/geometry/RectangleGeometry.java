package physics.geometry;

import com.google.common.collect.Lists;
import gizmo.GamePanel;
import lombok.Data;
import physics.Vector;
import physics.interfaces.MotionInterface;
import physics.interfaces.OperateInterface;
import physics.interfaces.PrintInterface;
import physics.math.MathUtils;

import java.awt.*;
import java.util.List;

@Data
public class RectangleGeometry extends TwoPointGeometry implements PrintInterface, OperateInterface, MotionInterface {
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
        temp = Lists.newArrayList();
        temp.add(point1);
        temp.add(new PointGeometry(point1.x > point2.x ? point1.x : point2.x, point1.y < point2.y ? point1.y : point2.y));
        temp.add(point2);
        temp.add(new PointGeometry(point1.x < point2.x ? point1.x : point2.x, point1.y > point2.y ? point1.y : point2.y));
        resetWidthAndHeight();
    }

    @Override
    public void move(double dx, double dy) {
        point1.x += dx;
        point1.y += dy;
        point2.x += dx;
        point2.y += dy;
        reset();
        //reset会设置
//        List<PointGeometry> tmp = getTemp();
//        for (PointGeometry p : tmp) {
//            p.x += dx;
//            p.y += dy;
//        }
    }

    @Override
    public void delete() {

    }

    @Override
    public void rotate(double angle) {
        PointGeometry center = new PointGeometry((temp.get(0).getX() + temp.get(2).getX())/2,
                (temp.get(0).getY() + temp.get(2).getY())/2);
        for (PointGeometry p : temp) {
            MathUtils.rotatePoint(p,center,angle);
        }

    }

    @Override
    public void zoom(double ratio) {
        PointGeometry center = new PointGeometry((temp.get(0).getX() + temp.get(2).getX())/2,
                (temp.get(0).getY() + temp.get(2).getY())/2);
        point1.x = (point1.x-center.x)*ratio + center.x;
        point1.y = (point1.y-center.y)*ratio + center.y;
        point2.x = (point2.x-center.x)*ratio + center.x;
        point2.y = (point2.y-center.y)*ratio + center.y;
//        for (PointGeometry p : temp) {
//            p.x += (p.x-center.x)*ratio + center.x;
//            p.y += (p.y-center.y)*ratio + center.y;
//        }
//        width = temp.get(3).x - temp.get(0).x;
//        height = temp.get(3).y - temp.get(0).y;
//        width = Math.sqrt(Math.pow(temp.get(1).x - temp.get(0).x, 2) + Math.pow(temp.get(1).y - temp.get(0).y, 2));
//        height = Math.sqrt(Math.pow(temp.get(0).x - temp.get(2).x, 2) + Math.pow(temp.get(0).y - temp.get(2).y, 2));
        reset();
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

    @Override
    public RectangleGeometry clone() {
        return (RectangleGeometry)super.clone();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("RectangleGeometry[\n");
        sb.append("super:").append(super.toString());
        sb.append("width:").append(width).append(";\n");
        sb.append("height:").append(height).append(";\n");
        sb.append("]\n");

        return sb.toString();
    }

    @Override
    public void update() {
        point1.x += velocity.getX() / GamePanel.FRAMES_PER_SECOND;
        point1.y += velocity.getY() / GamePanel.FRAMES_PER_SECOND;
        point2.x += velocity.getX() / GamePanel.FRAMES_PER_SECOND;
        point2.y += velocity.getY() / GamePanel.FRAMES_PER_SECOND;
        for (PointGeometry p : temp) {
            p.x += velocity.getX() / GamePanel.FRAMES_PER_SECOND;
            p.y += velocity.getY() / GamePanel.FRAMES_PER_SECOND;
        }
        velocity.plus(new Vector(constantAcceleration).multiplyScalar(1.0 / GamePanel.FRAMES_PER_SECOND));
    }

    @Override
    public void setRotationSpeed(PointGeometry rotateCenter, double speed) {

    }

    @Override
    public void setInstantaneousAcceleration(Vector acceleration) {
        velocity.plus(acceleration);
    }
}
