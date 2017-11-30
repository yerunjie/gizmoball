package physics.geometry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import physics.interfaces.CollisionInterface;
import physics.interfaces.PrintInterface;
import physics.math.MathUtils;

import java.awt.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class SegmentGeometry extends LineGeometry implements CollisionInterface{

    public SegmentGeometry(PointGeometry point1, PointGeometry point2) {
        super(point1, point2);
    }


    @Override
    public SegmentGeometry clone(){
        return (SegmentGeometry)super.clone();
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer("SegmentGeometry[\n");
        sb.append("super:").append(super.toString());
        sb.append("]\n");

        return sb.toString();
    }

    @Override
    public boolean onCollision(CircleGeometry ball) {
        if(MathUtils.calculatePointToLineDistance(ball.getCenter(),this)<=ball.getR()){
            ball.setInstantaneousAcceleration(
                    ball.getVelocity().takeFrom(
                            ball.getVelocity().projection(this)).negate().multiplyScalar(2)
            );
            return true;
        }
        return false;
    }


}
