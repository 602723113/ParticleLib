package top.zoyn.particlelib.coordinate;

import org.bukkit.Location;
import top.zoyn.particlelib.utils.LocationUtils;

/**
 * 自动修正在XZ平面上的粒子朝向
 *
 * @author Zoyn
 */
public class PlayerFixedCoordinate {

    /**
     * 原点
     */
    private final Location originDot;
    /**
     * 旋转角度
     */
    private final double rotateAngle;

    public PlayerFixedCoordinate(Location playerLocation) {
        // 旋转的角度
        rotateAngle = playerLocation.getYaw();
        originDot = playerLocation.clone();
        originDot.setPitch(0);
        // 重设仰俯角, 防止出现仰头后旋转角度不正确的问题
    }

    public Location getOriginDot() {
        return originDot;
    }

    public Location newLocation(double x, double y, double z) {
        return LocationUtils.rotateLocationAboutPoint(originDot.clone().add(-x, y, z), rotateAngle, originDot);
    }
}