package physics.interfaces;

import physics.Vector;
import physics.geometry.PointGeometry;

public interface MotionInterface {

    /**
     * update单位时间的位置
     */
    void update();

    /**
     * 旋转速度
     * @param rotateCenter
     * @param speed 正表示顺时针，负表示逆时针
     */
    void setRotationSpeed(PointGeometry rotateCenter, double speed);

    /**
     * 瞬时加速度
     * @param acceleration
     */
    void setInstantaneousAcceleration(Vector acceleration);

    /**
     * 恒定加速度（重力）
     * @param acceleration
     */
    void setConstantAcceleration(Vector acceleration);
}
