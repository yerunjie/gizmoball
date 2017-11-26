package physics.geometry;

import lombok.Data;

import java.util.List;

@Data
public abstract class TwoPointGeometry extends Geometry {
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
    public Object clone(){
        TwoPointGeometry newObject = (TwoPointGeometry) super.clone();
        newObject.setPoint1((PointGeometry) getPoint1().clone());
        newObject.setPoint2((PointGeometry) getPoint2().clone());
        return newObject;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer("TwoPointGeometry[\n");
        sb.append("super:").append(super.toString());
        sb.append("point1:").append(point1.toString());
        sb.append("point2:").append(point2.toString());

        sb.append("]\n");

        return sb.toString();
    }
}
