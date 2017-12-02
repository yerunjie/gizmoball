package physics.geometry;

import lombok.Data;
import physics.Vector;

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
        /*double dy = point1.y - point2.y;
        double dx = point1.x - point2.x;
        if (dx == 0) {
            slope = Double.MAX_VALUE;
            intercept = point1.y;
        } else {
            slope = dy / dx;
            intercept = point1.y - slope * point1.x;
        }*/
    }



    /*public LineGeometry(double slope, double intercept) {
        this.slope = slope;
        this.intercept = intercept;
    }*/

    @Override
    public LineGeometry clone(){
        return (LineGeometry)super.clone();
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer("LineGeometry[\n");
        sb.append("super:").append(super.toString());
        sb.append("a:").append(a).append(";\n");
        sb.append("b:").append(b).append(";\n");
        sb.append("c:").append(c).append(";\n");
        sb.append("]\n");

        return sb.toString();
    }

    public static void lineCollisionProcess(int collisionPoint,CircleGeometry ball,PointGeometry p1,PointGeometry p2){
        if (collisionPoint==1){
            ball.setInstantaneousAcceleration(
                    ball.getVelocity().takeFrom(
                            ball.getVelocity().projection(new LineGeometry(p1,p2))).negate().multiplyScalar(2)
            );
        }
        else if (collisionPoint==2){
            Vector pro=ball.getVelocity().projection(new LineGeometry(ball.getCenter(),p1));
            ball.setInstantaneousAcceleration(pro.negate().multiplyScalar(2));
        }
        else {
            Vector pro=ball.getVelocity().projection(new LineGeometry(ball.getCenter(),p2));
            ball.setInstantaneousAcceleration(pro.negate().multiplyScalar(2));
        }
    }
}
