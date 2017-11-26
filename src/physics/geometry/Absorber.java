package physics.geometry;

public class Absorber extends RectangleGeometry{
    public Absorber(PointGeometry point1, PointGeometry point2) {
        super(point1, point2);
    }
    @Override
    public Object clone(){
        return super.clone();
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer("Absorber[\n");
        sb.append("super:").append(super.toString());
        sb.append("]\n");

        return sb.toString();
    }
}
