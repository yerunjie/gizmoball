package physics.geometry;

import com.google.common.collect.Lists;
import lombok.Data;
import physics.Vector;
import physics.interfaces.CollisionInterface;
import physics.interfaces.MotionInterface;
import physics.interfaces.OperateInterface;
import physics.interfaces.PrintInterface;
import physics.math.MathUtils;

import java.awt.*;
import java.util.List;
import java.util.Map;

@Data
public class RectangleGeometry extends TwoPointGeometry implements PrintInterface, OperateInterface,MotionInterface,CollisionInterface {
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

    }

    @Override
    public void setRotationSpeed(PointGeometry rotateCenter, double speed) {

    }

    @Override
    public void setInstantaneousAcceleration(Vector acceleration) {

    }

    @Override
    public boolean onCollision(CircleGeometry ball) {

        if(MathUtils.calculatePointToLineDistance(ball.center,new LineGeometry( //top
                point1,new PointGeometry(point2.getX(),point1.getY()))).getDistance()<= ball.r){
            LineGeometry.lineCollisionProcess(
                    MathUtils.calculatePointToLineDistance(ball.center,new LineGeometry(point1,new PointGeometry(point2.getX(),point1.getY()))).getCollisionPoint(),
                    ball,point1,new PointGeometry(point2.getX(),point1.getY()));
            return true;
        }
        else if(MathUtils.calculatePointToLineDistance(ball.center,new LineGeometry( //top
                point1,new PointGeometry(point1.getX(),point2.getY()))).getDistance()<= ball.r){
            LineGeometry.lineCollisionProcess(
                    MathUtils.calculatePointToLineDistance(ball.center,new LineGeometry(point1,new PointGeometry(point1.getX(),point2.getY()))).getCollisionPoint(),
                    ball,point1,new PointGeometry(point1.getX(),point2.getY()));
            return true;
        }
        else if(MathUtils.calculatePointToLineDistance(ball.center,new LineGeometry( //top
                point2,new PointGeometry(point1.getX(),point2.getY()))).getDistance()<= ball.r){
            LineGeometry.lineCollisionProcess(
                    MathUtils.calculatePointToLineDistance(ball.center,new LineGeometry(point2,new PointGeometry(point1.getX(),point2.getY()))).getCollisionPoint(),
                    ball,point2,new PointGeometry(point1.getX(),point2.getY()));
            return true;
        }
        else if(MathUtils.calculatePointToLineDistance(ball.center,new LineGeometry( //top
                point2,new PointGeometry(point2.getX(),point1.getY()))).getDistance()<= ball.r){
            LineGeometry.lineCollisionProcess(
                    MathUtils.calculatePointToLineDistance(ball.center,new LineGeometry(point2,new PointGeometry(point2.getX(),point1.getY()))).getCollisionPoint(),
                    ball,point2,new PointGeometry(point2.getX(),point1.getY()));
            return true;
        }
//        else if(MathUtils.calculatePointToLineDistance(ball.center,new LineGeometry( //left
//                point1,new PointGeometry(point1.getX(),point2.getY())))<= ball.r){
//            ball.setInstantaneousAcceleration(new Vector(-2*ball.getVelocity().getX(),0));
//            return true;
//        }
//        else if(MathUtils.calculatePointToLineDistance(ball.center,new LineGeometry( //bottom
//                point2,new PointGeometry(point1.getX(),point2.getY())))<= ball.r){
//            ball.setInstantaneousAcceleration(new Vector(0,-2*ball.getVelocity().getY()));
//            return true;
//        }
//        else if(MathUtils.calculatePointToLineDistance(ball.center,new LineGeometry( //right
//                point2,new PointGeometry(point2.getX(),point1.getY())))<= ball.r){
//            ball.setInstantaneousAcceleration(new Vector(-2*ball.getVelocity().getX(),0));
//            return true;
//        }

        return false;
    }


}
