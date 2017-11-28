package physics;

import lombok.Data;

@Data
public class Vector implements Cloneable {
    //public static final Vector ZERO = new Vector(0, 0);
    protected double x;
    protected double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Vector clone() {
        Vector newObject = null;
        try {
            newObject = (Vector) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return newObject;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Vector[\n");
        sb.append("x:").append(x).append(";\n");
        sb.append("y:").append(y).append(";\n");

        sb.append("]\n");

        return sb.toString();
    }

    public double getNorm() {
        double temp = x * x + y * y;
        return temp == 0 ? 0 : Math.sqrt(temp);
    }

    public void plus(Vector another) {
        x += another.x;
        y += another.y;
    }

    public Vector multiplyScalar(double s) {
        x *= s;
        y *= s;
        return this;
    }

    public Vector(Vector another) {
        x = another.x;
        y = another.y;
    }

}
