package top.zoyn.particlelib.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * 向量工具类
 *
 * @author Zoyn
 */
public class VectorUtils {

    /**
     * 只通过数字本身相减得到向量, 减少额外克隆的损耗
     *
     * @param start 起点
     * @param end   终点
     * @return {@link Vector}
     */
    public static Vector createVector(Location start, Location end) {
        return new Vector(end.getX() - start.getX(), end.getY() - start.getY(), end.getZ() - start.getZ());
    }

    public static Vector getLeftDirection(Location location) {
        return rotateAroundAxisY(location.getDirection().clone(), 90);
    }

    public static Vector getRightDirection(Location location) {
        return rotateAroundAxisY(location.getDirection().clone(), -90);
    }

    /**
     * 得到一个单位为 1 的向上的向量
     *
     * @return {@link Vector}
     */
    public static Vector getUpVector() {
        return getUpVector(1);
    }

    public static Vector getUpVector(double multiply) {
        return new Vector(0, 1, 0).multiply(multiply);
    }

    public static Vector rotateAroundAxisX(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double y = v.getY() * cos - v.getZ() * sin;
        double z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    public static Vector rotateAroundAxisY(Vector v, double angle) {
        angle = -angle;
        angle = Math.toRadians(angle);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    public static Vector rotateAroundAxisZ(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = v.getX() * cos - v.getY() * sin;
        double y = v.getX() * sin + v.getY() * cos;
        return v.setX(x).setY(y);
    }

    /**
     * This handles non-unit vectors, with yaw and pitch instead of X,Y,Z angles.
     * <p>
     * Thanks to SexyToad!
     * <p>
     * 将一个非单位向量使用yaw和pitch来代替X, Y, Z的角旋转方式
     *
     * @param v            向量
     * @param yawDegrees   yaw的角度
     * @param pitchDegrees pitch的角度
     * @return {@link Vector}
     */
    public static Vector rotateVector(Vector v, float yawDegrees, float pitchDegrees) {
        double yaw = Math.toRadians(-1 * (yawDegrees + 90));
        double pitch = Math.toRadians(-pitchDegrees);

        double cosYaw = Math.cos(yaw);
        double cosPitch = Math.cos(pitch);
        double sinYaw = Math.sin(yaw);
        double sinPitch = Math.sin(pitch);

        double initialX, initialY, initialZ;
        double x, y, z;

        // Z_Axis rotation (Pitch)
        initialX = v.getX();
        initialY = v.getY();
        x = initialX * cosPitch - initialY * sinPitch;
        y = initialX * sinPitch + initialY * cosPitch;

        // Y_Axis rotation (Yaw)
        initialZ = v.getZ();
        initialX = x;
        z = initialZ * cosYaw - initialX * sinYaw;
        x = initialZ * sinYaw + initialX * cosYaw;

        return new Vector(x, y, z);
    }

    /**
     * 判断一个向量是否已单位化
     *
     * @param vector 向量
     * @return 是否单位化
     */
    public static boolean isNormalized(Vector vector) {
        return Math.abs(vector.lengthSquared() - 1) < Vector.getEpsilon();
    }

    /**
     * 空间向量绕任一向量旋转
     *
     * @param vector 待旋转向量
     * @param axis   旋转轴向量
     * @param angle  旋转角度
     * @return {@link Vector}
     */
    public static Vector rotateAroundAxis(Vector vector, Vector axis, double angle) {
        return rotateAroundNonUnitAxis(vector, isNormalized(axis) ? axis : axis.clone().normalize(), angle);
    }

    /**
     * 空间向量绕任一向量旋转
     * <p>注: 这里的旋转轴必须为已单位化才可使用!</p>
     * <p>
     * 罗德里格旋转公式: https://zh.wikipedia.org/wiki/%E7%BD%97%E5%BE%B7%E9%87%8C%E6%A0%BC%E6%97%8B%E8%BD%AC%E5%85%AC%E5%BC%8F
     * <p>
     * 正常人能看懂的: https://www.cnblogs.com/wubugui/p/3734627.html
     *
     * @param vector 要旋转的向量
     * @param axis   旋转轴向量
     * @param angle  旋转角度
     * @return {@link Vector}
     */
    public static Vector rotateAroundNonUnitAxis(Vector vector, Vector axis, double angle) {
        double x = vector.getX(), y = vector.getY(), z = vector.getZ();
        double x2 = axis.getX(), y2 = axis.getY(), z2 = axis.getZ();

        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);
        double dotProduct = vector.dot(axis);

        double xPrime = x2 * dotProduct * (1d - cosTheta)
                + x * cosTheta
                + (-z2 * y + y2 * z) * sinTheta;
        double yPrime = y2 * dotProduct * (1d - cosTheta)
                + y * cosTheta
                + (z2 * x - x2 * z) * sinTheta;
        double zPrime = z2 * dotProduct * (1d - cosTheta)
                + z * cosTheta
                + (-y2 * x + x2 * y) * sinTheta;

        return vector.setX(xPrime).setY(yPrime).setZ(zPrime);
    }

}
