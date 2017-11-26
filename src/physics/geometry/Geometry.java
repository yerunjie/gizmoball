package physics.geometry;

import com.google.common.collect.Lists;
import lombok.Data;
import physics.Vector;

import java.util.List;

@Data
public abstract class Geometry {
    private static int count = 0;
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
    }

    public abstract void reset(List<PointGeometry> pointGeometries);

}
