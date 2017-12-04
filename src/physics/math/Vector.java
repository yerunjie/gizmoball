package physics.math;

import lombok.Data;
import physics.geometry.LineGeometry;
import physics.geometry.PointGeometry;

import java.io.Serializable;

@Data
public class Vector implements Cloneable ,Serializable {
    public static final Vector ZERO = new Vector(0, 0);
    protected double x;
    protected double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(PointGeometry p1, PointGeometry p2) {
        this.x = p2.getX() - p1.getX();
        this.y = p2.getY() - p1.getY();
    }

    public Vector(Vector another) {
        x = another.x;
        y = another.y;
    }

    public Vector(Vector another, double norm) {
        if (norm != 0 && another.getNorm() != 0) {
            double r = norm / another.getNorm();
            x = another.x * r;
            y = another.y * r;
        } else {
            x = 0;
            y = 0;
        }
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

    public Vector setNorm(double norm) {
        if (norm != 0 && getNorm() != 0) {
            double r = norm / getNorm();
            x = x * r;
            y = y * r;
        } else {
            x = 0;
            y = 0;
        }
        return this;
    }

    public double getNorm() {
        double temp = x * x + y * y;
        return temp == 0 ? 0 : Math.sqrt(temp);
    }

    public Vector plus(Vector another) {
        x += another.x;
        y += another.y;
        return new Vector(x, y);
    }

    public Vector takeFrom(Vector another) {
        return new Vector(this.x - another.x, this.y - another.y);
    }

    public Vector multiplyScalar(double s) {
        x *= s;
        y *= s;
        return this;
    }

    public double dotProduct(Vector v1, Vector v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    public Vector negate() {
        return new Vector(-x, -y);
    }

    public Vector projection(LineGeometry line) {
        Vector lineVector = new Vector(line.getPoint1().getX() - line.getPoint2().getX(),
                line.getPoint1().getY() - line.getPoint2().getY());
        if (dotProduct(lineVector, this) == 0) return new Vector(0, 0);
        if (dotProduct(lineVector, this) < 0) {
            double proNorm = Math.abs(dotProduct(lineVector, this)) / lineVector.getNorm();
            return new Vector(-lineVector.getX() / lineVector.getNorm() * proNorm,
                    -lineVector.getY() / lineVector.getNorm() * proNorm);
        } else {
            double proNorm = Math.abs(dotProduct(lineVector, this)) / lineVector.getNorm();
            return new Vector(lineVector.getX() / lineVector.getNorm() * proNorm,
                    lineVector.getY() / lineVector.getNorm() * proNorm);
        }
    }

}
