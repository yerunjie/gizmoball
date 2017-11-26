package physics.geometry;

public class Flipper extends RectangleGeometry {
    public Flipper(PointGeometry point1, PointGeometry point2) {
        super(point1, point2);
    }

    @Override
    public Flipper clone() throws CloneNotSupportedException {
        return (Flipper) super.clone();
    }
}
