package physics.geometry;

import lombok.Data;
import physics.interfaces.CollisionInterface;
import physics.interfaces.OperateInterface;
import physics.interfaces.PrintInterface;

import java.awt.*;
import java.util.List;

@Data
public class TriangleGeometry extends Geometry implements PrintInterface,OperateInterface {
    private PointGeometry point1;
    private PointGeometry point2;
    private PointGeometry point3;
    private int xpoints[];
    private int ypoints[];

    public TriangleGeometry(PointGeometry point1, PointGeometry point2, PointGeometry point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        xpoints = new int[3];
        ypoints = new int[3];
        reset();
    }

    public void reset() {
        xpoints[0] = (int) point1.x;
        xpoints[1] = (int) point2.x;
        xpoints[2] = (int) point3.x;
        ypoints[0] = (int) point1.y;
        ypoints[1] = (int) point2.y;
        ypoints[2] = (int) point3.y;
    }

    @Override
    public void move(double dx, double dy) {

    }

    @Override
    public void delete() {

    }

    @Override
    public void rotate(double angle) {

    }

    @Override
    public void zoom(double ratio) {

    }

    @Override
    public boolean isInside(PointGeometry pointGeometry) {
        int i, j = 2;
        boolean oddNodes = false;

        for (i = 0; i < 3; i++) {
            if ((ypoints[i] < pointGeometry.y && ypoints[j] >= pointGeometry.y
                    || ypoints[j] < pointGeometry.y && ypoints[i] >= pointGeometry.y)
                    && (xpoints[i] <= pointGeometry.x || xpoints[j] <= pointGeometry.x)) {
                oddNodes ^= (xpoints[i] + (pointGeometry.y - ypoints[i]) / (ypoints[j] - ypoints[i]) * (xpoints[j] - xpoints[i]) < pointGeometry.x);
            }
            j = i;
        }

        return oddNodes;
    }

    @Override
    public void print(Color color, Graphics g) {
        g.setColor(color);
        g.drawPolygon(xpoints, ypoints, 3);
    }

    @Override
    public void reset(List<PointGeometry> pointGeometries) {
        if (pointGeometries.size() != 3) {
            temp = pointGeometries;
            return;
        }
        point1 = pointGeometries.get(0);
        point2 = pointGeometries.get(1);
        point3 = pointGeometries.get(2);
        reset();
    }

    @Override
    public void drawing(Color color, Graphics g) {
        g.setColor(color);
        for (int i = 0; i < temp.size() - 1; i++) {
            g.drawLine((int) temp.get(i).x,
                    (int) temp.get(i).y,
                    (int) temp.get(i + 1).x,
                    (int) temp.get(i + 1).y);
        }
    }

    @Override
    public TriangleGeometry clone(){
        TriangleGeometry newObject = (TriangleGeometry) super.clone();
        newObject.setPoint1((PointGeometry) getPoint1().clone());
        newObject.setPoint2((PointGeometry) getPoint2().clone());
        newObject.setPoint3((PointGeometry) getPoint3().clone());
        return newObject;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer("TriangleGeometry[\n");
        sb.append("super:").append(super.toString());
        sb.append("point1:").append(point1.toString());
        sb.append("point2:").append(point2.toString());
        sb.append("point3:").append(point3.toString());
        sb.append("xpoints:").append("[");
        for (int i=0; i<xpoints.length; i++){
            sb.append(xpoints[i]).append(",");
        }
        sb.append("]\n");
        sb.append("ypoints:").append("[");
        for (int i=0; i<ypoints.length; i++){
            sb.append(ypoints[i]).append(",");
        }
        sb.append("]\n");

        sb.append("]\n");

        return sb.toString();
    }
}
