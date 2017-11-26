package physics.interfaces;

import physics.geometry.CircleGeometry;

public interface CollisionInterface {
    boolean onCollision(CircleGeometry ball);
}
