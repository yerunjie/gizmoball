package physics.geometry;

import com.google.common.collect.Lists;
import gizmo.GamePanel;
import lombok.Data;
import physics.interfaces.CollisionInterface;
import physics.interfaces.OperateInterface;
import physics.interfaces.PrintInterface;
import physics.math.MathUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;


@Data
public class Track extends Geometry implements PrintInterface, CollisionInterface, OperateInterface {
    private PointGeometry start;
    private PointGeometry end;

    @Override
    public void move(double dx, double dy) {
        start.x += dx;
        start.y += dy;
        end.x += dx;
        end.y += dy;
        for (PointGeometry pointGeometry : pointGeometries) {
            pointGeometry.x += dx;
            pointGeometry.y += dy;
        }
    }

    @Override
    public void delete() {

    }

    @Override
    public void rotate(double angle) {
        PointGeometry center = getCenter();
        for (PointGeometry p : pointGeometries) {
            MathUtils.rotatePoint(p, center, angle);
        }
    }

    @Override
    public void zoom(double ratio) {
        PointGeometry center = getCenter();
        for (PointGeometry pointGeometry : pointGeometries) {
            pointGeometry.x = (pointGeometry.x - center.x) * ratio + center.x;
            pointGeometry.y = (pointGeometry.y - center.y) * ratio + center.y;
        }
    }

    @Override
    public boolean isInside(PointGeometry pointGeometry) {
        IntSummaryStatistics xStatistics = pointGeometries.stream().mapToInt(point -> (int) point.x).summaryStatistics();
        IntSummaryStatistics yStatistics = pointGeometries.stream().mapToInt(point -> (int) point.y).summaryStatistics();
        if (pointGeometry.x < xStatistics.getMax() && pointGeometry.x > xStatistics.getMin()) {
            if (pointGeometry.y < yStatistics.getMax() && pointGeometry.y > yStatistics.getMin()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void print(Color color, Graphics g) {
        g.setColor(color);
        if (GamePanel.status == 2) {
            double r = GamePanel.tempBall.r;
            ((Graphics2D) g).setStroke(new BasicStroke((float) r * 2));
            g.fillOval((int) (start.x - r), (int) (start.y - r), 2 * (int) r, 2 * (int) r);
            g.fillOval((int) (end.x - r), (int) (end.y - r), 2 * (int) r, 2 * (int) r);
        }
        g.drawPolyline(pointGeometries.stream().mapToInt(point -> (int) point.x).toArray(),
                pointGeometries.stream().mapToInt(point -> (int) point.y).toArray(), pointGeometries.size());
        ((Graphics2D) g).setStroke(new BasicStroke(1.0f));
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
        IntSummaryStatistics xStatistics = pointGeometries.stream().mapToInt(point -> (int) point.x).summaryStatistics();
        IntSummaryStatistics yStatistics = pointGeometries.stream().mapToInt(point -> (int) point.y).summaryStatistics();
        return new PointGeometry((xStatistics.getMin() + xStatistics.getMax()) / 2, (yStatistics.getMin() + yStatistics.getMax()) / 2);
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
