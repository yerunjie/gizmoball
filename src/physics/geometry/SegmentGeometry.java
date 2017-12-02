package physics.geometry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import physics.Vector;
import physics.interfaces.CollisionInterface;
import physics.interfaces.PrintInterface;
import physics.math.MathUtils;

import java.awt.*;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class SegmentGeometry extends LineGeometry implements CollisionInterface{

    private boolean topbottom;
    public SegmentGeometry(PointGeometry point1, PointGeometry point2,boolean topbottom) {
        super(point1, point2);
        this.topbottom=topbottom;
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

        if(MathUtils.calculatePointToLineDistance(ball.getCenter(),this).getDistance()<=ball.getR()){
            if(this.topbottom==true){
                ball.setInstantaneousAcceleration(new Vector(0,-ball.getVelocity().getY()*2));
            }
            else{
                ball.setInstantaneousAcceleration(new Vector(-ball.getVelocity().getX()*2,0));
            }
            return true;
        }
        return false;
    }


}
