package top.zoyn.particlelib.utils;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * 坐标工具类
 *
 * @author Zoyn
 */
public class LocationUtils {

    /**
     * 在二维平面上利用给定的中心点逆时针旋转一个点
     *
     * @param location 待旋转的点
     * @param angle    旋转角度
     * @param point    中心点
     * @return {@link Location}
     */
    public static Location rotateLocationAboutPoint(Location location, double angle, Location point) {
        double radians = Math.toRadians(angle);
        double dx = location.getX() - point.getX();
        double dz = location.getZ() - point.getZ();

        double newX = dx * Math.cos(radians) - dz * Math.sin(radians) + point.getX();
        double newZ = dz * Math.cos(radians) + dx * Math.sin(radians) + point.getZ();
        return new Location(location.getWorld(), newX, location.getY(), newZ);
    }

    public static Location rotateLocationAboutVector(Location location, Location origin, double angle, Vector axis) {
        Vector vector = location.clone().subtract(origin).toVector();
        return origin.clone().add(vector.rotateAroundAxis(axis, angle));
    }

    /**
     * 判断一个是否处在实体面向的扇形区域内
     * <p>
     * 通过反三角算向量夹角的算法
     *
     * @param target       目标坐标
     * @param livingEntity 实体
     * @param radius       扇形半径
     * @param angle        扇形角度
     * @return 如果处于扇形区域则返回 true
     */
    public static boolean isPointInEntitySector(Location target, LivingEntity livingEntity, double radius, double angle) {
        Vector v1 = livingEntity.getLocation().getDirection();
        Vector v2 = target.clone().subtract(livingEntity.getLocation()).toVector();

        double cosTheta = v1.dot(v2) / (v1.length() * v2.length());
        double degree = Math.toDegrees(Math.acos(cosTheta));
        // 距离判断
        if (target.distance(livingEntity.getLocation()) < radius) {
            // 向量夹角判断
            return degree < angle * 0.5f;
        }
        return false;
    }

    /**
     * 判断一个是否处在实体面向的扇形区域内
     * <p>
     * 通过叉乘算法
     *
     * @param target       目标坐标
     * @param livingEntity 实体
     * @param radius       扇形半径
     * @param angle        扇形角度
     * @return 如果处于扇形区域则返回 true
     */
    public static boolean isInsideSector(Location target, LivingEntity livingEntity, double radius, double angle) {
        Vector sectorStart = VectorUtils.rotateAroundAxisY(livingEntity.getLocation().getDirection().clone(), -angle / 2);
        Vector sectorEnd = VectorUtils.rotateAroundAxisY(livingEntity.getLocation().getDirection().clone(), angle / 2);

        Vector v = target.clone().subtract(livingEntity.getLocation()).toVector();

        boolean start = -sectorStart.getX() * v.getZ() + sectorStart.getZ() * v.getX() > 0;
        boolean end = -sectorEnd.getX() * v.getZ() + sectorEnd.getZ() * v.getX() > 0;
        return !start && end && target.distance(livingEntity.getLocation()) < radius;
    }
}
