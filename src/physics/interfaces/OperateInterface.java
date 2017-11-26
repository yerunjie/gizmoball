package physics.interfaces;

import physics.geometry.PointGeometry;

public interface OperateInterface {
    void move(double dx, double dy);

    void delete();

    void rotate(double angle);

    void zoom(double ratio);

    boolean isInside(PointGeometry pointGeometry);
    //void setPosition(PointGeometry position);
}
