package physics.geometry;

import com.google.common.collect.Lists;
import lombok.Data;
import physics.Vector;

import java.awt.*;
import java.util.IntSummaryStatistics;
import java.util.List;

import static gizmo.Constant.centerPointRadius;

@Data
public abstract class Geometry implements Cloneable {
    private static int count = 0;
    public static int FRAMES_PER_SECOND = 100;

    protected Vector constantAcceleration;
    protected double frictionCoefficient;
    protected Vector velocity;

    protected synchronized static int generateId() {
        count++;
        return count;
    }

    protected int id;
    protected List<PointGeometry> temp;

    protected Geometry() {
        id = generateId();
        temp = Lists.newArrayList();
        frictionCoefficient = 0;
        constantAcceleration = new Vector(0, 0);
        velocity = new Vector(0, 0);
    }

    public abstract void reset(List<PointGeometry> pointGeometries);


    public abstract PointGeometry getCenter();

    public void printEditBound(Graphics g) {
        IntSummaryStatistics xStatistics = temp.stream().mapToInt(point -> (int) point.x).summaryStatistics();
        IntSummaryStatistics yStatistics = temp.stream().mapToInt(point -> (int) point.y).summaryStatistics();
        g.setColor(Color.cyan);
        g.drawRect(xStatistics.getMin(), yStatistics.getMin(), xStatistics.getMax() - xStatistics.getMin(), yStatistics.getMax() - yStatistics.getMin());
        PointGeometry center = getCenter();
        g.drawOval((int) center.x - centerPointRadius, (int) center.y - centerPointRadius, 2 * centerPointRadius, 2 * centerPointRadius);
    }

    @Override
    public Geometry clone() {
        Geometry newObject = null;
        try {
            newObject = (Geometry) super.clone();
            newObject.setConstantAcceleration((Vector) getConstantAcceleration().clone());
            newObject.setVelocity((Vector) getVelocity().clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return newObject;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Geometry[\n");
        sb.append("count:").append(count).append(";\n");
        sb.append("constantAcceleration:").append(constantAcceleration.toString()).append(";\n");
        sb.append("frictionCoefficient:").append(frictionCoefficient).append(";\n");
        sb.append("velocity:").append(velocity.toString()).append(";\n");
        sb.append("]\n");

        return sb.toString();
    }
}
