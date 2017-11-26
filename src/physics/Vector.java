package physics;

import lombok.Data;

@Data
public class Vector {
    protected double x;
    protected double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
