package physics.geometry;

import lombok.Data;

import java.awt.*;

@Data
public abstract class Geometry {
    protected int id;
    public abstract boolean isInside(PointGeometry pointGeometry);
    public abstract void print(Color color,Graphics g);
}
