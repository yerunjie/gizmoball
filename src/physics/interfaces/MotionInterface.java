package physics.interfaces;

import physics.math.Vector;
import physics.geometry.Geometry;
import physics.geometry.PointGeometry;

public interface MotionInterface extends PrintInterface {

    /**
     * update单位时间的位置
     */
    void update();

    default void reUpdate() {
        Geometry geometry = (Geometry) this;
        geometry.setVelocity(geometry.getVelocity().negate());
        update();
        geometry.setVelocity(geometry.getVelocity().negate());
    }

    /**
     * update单位时间的速度
     */
    void updateVelocity();

    /**
     * 旋转速度
     *
     * @param rotateCenter
     * @param speed        正表示顺时针，负表示逆时针
     */
    void setRotationSpeed(PointGeometry rotateCenter, double speed);

    /**
     * 瞬时加速度
     *
     * @param acceleration
     */
    void setInstantaneousAcceleration(Vector acceleration);

    /**
     * 恒定加速度（重力）
     *
     * @param acceleration
     */
    void setConstantAcceleration(Vector acceleration);
}
