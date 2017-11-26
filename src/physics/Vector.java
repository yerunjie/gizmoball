package physics;

import lombok.Data;

@Data
public class Vector implements Cloneable{
    protected double x;
    protected double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Object clone(){
        Vector newObject = null;
        try {
            newObject = (Vector) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return newObject;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer("Vector[\n");
        sb.append("x:").append(x).append(";\n");
        sb.append("y:").append(y).append(";\n");

        sb.append("]\n");

        return sb.toString();
    }
}
