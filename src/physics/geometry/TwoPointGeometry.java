package physics.geometry;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public abstract class TwoPointGeometry extends Geometry implements Serializable{
    protected PointGeometry point1;
    protected PointGeometry point2;

    public TwoPointGeometry(PointGeometry point1, PointGeometry point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    @Override
    public void reset(List<PointGeometry> pointGeometries) {
        if (pointGeometries.size() != 2) {
            throw new IllegalArgumentException();
        }
        point1 = pointGeometries.get(0);
        point2 = pointGeometries.get(1);
    }

    @Override
    public TwoPointGeometry clone() {
        TwoPointGeometry newObject = (TwoPointGeometry) super.clone();
        newObject.setPoint1((PointGeometry) getPoint1().clone());
        newObject.setPoint2((PointGeometry) getPoint2().clone());
        return newObject;
    }

}
