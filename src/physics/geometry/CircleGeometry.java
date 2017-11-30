package physics.geometry;

import gizmo.GamePanel;
import lombok.Data;
import physics.Vector;
import physics.interfaces.CollisionInterface;
import physics.interfaces.MotionInterface;
import physics.interfaces.OperateInterface;
import physics.interfaces.PrintInterface;
import physics.math.MathUtils;

import java.awt.*;

import static physics.math.MathUtils.distanceBetweenTwoPoints;

@Data
public class CircleGeometry extends RectangleGeometry implements PrintInterface, OperateInterface, MotionInterface,CollisionInterface {
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

    @Override
    public CircleGeometry clone() {
        CircleGeometry newObject = (CircleGeometry) super.clone();
        newObject.setCenter((PointGeometry) newObject.getCenter().clone());
        return newObject;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("CircleGeometry[\n");
        sb.append("super:").append(super.toString());
        sb.append("    r:").append(r).append(";\n");
        sb.append("    center:").append(center.toString()).append(";\n");
        sb.append("]\n");

        return sb.toString();
    }

    @Override
    public void update() {
        center.x += velocity.getX() / GamePanel.FRAMES_PER_SECOND;
        center.y += velocity.getY() / GamePanel.FRAMES_PER_SECOND;
        velocity.plus(new Vector(constantAcceleration).multiplyScalar(1.0 / GamePanel.FRAMES_PER_SECOND));
    }

    @Override
    public void setRotationSpeed(PointGeometry rotateCenter, double speed) {
    }

    @Override
    public void setInstantaneousAcceleration(Vector acceleration) {
        velocity.plus(acceleration);
    }

    @Override
    public boolean onCollision(CircleGeometry ball) {
        if(MathUtils.distanceBetweenTwoPoints(ball.getCenter(),this.center)<=this.r+ball.getR()){
            Vector pro=ball.getVelocity().projection(new LineGeometry(ball.getCenter(),this.center));
            ball.setInstantaneousAcceleration(pro.negate().multiplyScalar(2));
            return true;
        }
        return false;
    }
}
