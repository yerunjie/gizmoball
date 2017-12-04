package physics.geometry;

import gizmo.GamePanel;
import lombok.Data;
import lombok.NoArgsConstructor;
import physics.math.Vector;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

@Data
@NoArgsConstructor
public class Ball extends CircleGeometry implements Serializable {
    private Queue<PointGeometry> trackQueue;
    private PointGeometry pointGeometry;
    private boolean canCollision;

    public Ball(PointGeometry point1, PointGeometry point2) {
        super(point1, point2);
        trackQueue = new LinkedList<>();
        canCollision = true;
    }

    public void addTrack(Track track) {
        for (PointGeometry pointGeometry1 : track.getPointGeometries()) {
            trackQueue.add(new PointGeometry(pointGeometry1));
        }
        center = new PointGeometry(trackQueue.peek());
        pointGeometry = trackQueue.poll();
        velocity = new Vector(pointGeometry, trackQueue.peek()).setNorm(velocity.getNorm());
        canCollision = false;
    }

    @Override
    public void print(Color color, Graphics g) {
        super.print(Color.pink, g);
    }

    @Override
    public Ball clone() {
        Ball newBall = (Ball) super.clone();
        newBall.canCollision = true;
        newBall.trackQueue = new LinkedList<>();
        return newBall;
    }

    @Override
    public void update() {
        super.update();
        if (trackQueue.size() != 0) {
            double temp = new Vector(pointGeometry, center).getNorm();
            double length = new Vector(pointGeometry, trackQueue.peek()).getNorm();
            while (temp > length) {
                temp -= length;
                center = new PointGeometry(trackQueue.peek());
                pointGeometry = trackQueue.poll();
                if (trackQueue.size() == 0) {
                    canCollision = true;
                    break;
                } else {
                    length = new Vector(pointGeometry, trackQueue.peek()).getNorm();
                    velocity = new Vector(pointGeometry, trackQueue.peek()).setNorm(velocity.getNorm());
                }
            }
        }
    }

    @Override
    public void updateVelocity() {//不收阻力
        if (canCollision) {
            velocity.plus(new Vector(constantAcceleration).multiplyScalar(1.0 / GamePanel.FRAMES_PER_SECOND));
        }
    }
}
