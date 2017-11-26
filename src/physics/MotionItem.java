package physics;

import lombok.Data;
import physics.geometry.Geometry;

@Data
public class MotionItem<T extends Geometry> {
    private T item;
    private Vector constantAcceleration;
    private double frictionCoefficient;
    private Vector velocity;
}
