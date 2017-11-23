package physics.geometry;

import lombok.Data;

import java.awt.*;

@Data
public abstract class TwoPointGeometry extends Geometry{
    protected PointGeometry point1;
    protected PointGeometry point2;

    public TwoPointGeometry(PointGeometry point1, PointGeometry point2) {
        this.point1 = point1;
        this.point2 = point2;
    }
}
