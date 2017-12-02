package physics.math;

import lombok.Data;

/**
 * Created by 86761 on 2017/11/30.
 */
@Data
public class PointToLine{
    protected double distance;
    protected int collisionPoint; //1.边 2.point1 3.point2

    public PointToLine(double distance, int collisionPoint) {
        this.distance = distance;
        this.collisionPoint = collisionPoint;
    }
}