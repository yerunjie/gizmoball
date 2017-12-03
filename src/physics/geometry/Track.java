package physics.geometry;

import com.google.common.collect.Lists;
import lombok.Data;
import physics.interfaces.CollisionInterface;
import physics.interfaces.PrintInterface;
import physics.math.MathUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Data
public class Track extends Geometry implements PrintInterface, CollisionInterface {
    private PointGeometry start;
    private PointGeometry end;

    @Override
    public void print(Color color, Graphics g) {
        g.setColor(color);
        g.drawPolyline(pointGeometries.stream().mapToInt(point -> (int) point.x).toArray(),
                pointGeometries.stream().mapToInt(point -> (int) point.y).toArray(), pointGeometries.size());

    }

    @Override
    public void drawing(Color color, Graphics g) {
        g.setColor(color);
        g.drawPolyline(pointGeometries.stream().mapToInt(point -> (int) point.x).toArray(),
                pointGeometries.stream().mapToInt(point -> (int) point.y).toArray(), pointGeometries.size());
    }

    @Override
    public Track clone() {
        Track newTrack = (Track) super.clone();
        newTrack.pointGeometries = Lists.newArrayList();
        for (PointGeometry pointGeometry : pointGeometries) {
            newTrack.pointGeometries.add(new PointGeometry(pointGeometry));
        }
        return newTrack;
    }

    @Override
    public void reset(List<PointGeometry> pointGeometries) {
        this.pointGeometries = new ArrayList<>();
        start = new PointGeometry(pointGeometries.get(0));
        end = new PointGeometry(pointGeometries.get(pointGeometries.size() - 1));
        PointGeometry temp = start;
        this.pointGeometries.add(new PointGeometry(temp));
        for (PointGeometry pointGeometry : pointGeometries) {
            if (pointGeometry.isCloseTo(temp)) {
                continue;
            }
            temp = pointGeometry;
            this.pointGeometries.add(new PointGeometry(temp));
        }
    }

    @Override
    public PointGeometry getCenter() {
        return null;
    }

    @Override
    public boolean onCollision(CircleGeometry ball) {
        if (ball instanceof Ball && MathUtils.distanceBetweenTwoPoints(ball.getCenter(), start) < 2 * ball.getR()) {
            ball.setCenter(new PointGeometry(start));
            ((Ball) ball).addTrack(this);
            return true;
        }
        return false;
    }
}
