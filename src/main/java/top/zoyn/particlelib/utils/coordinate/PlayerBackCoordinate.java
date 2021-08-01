package top.zoyn.particlelib.utils.coordinate;

import org.bukkit.Location;
import top.zoyn.particlelib.utils.LocationUtils;

/**
 * 表示一个玩家后背坐标系
 * <p>将玩家背后转换为一个直角坐标系</p>
 *
 * @author Zoyn
 */
public class PlayerBackCoordinate implements Coordinate {

    private final Location originDot;
    private final double rotateAngle;

    public PlayerBackCoordinate(Location playerLocation) {
        // 旋转的角度
        rotateAngle = playerLocation.getYaw();
        originDot = playerLocation.clone();
        // 重设仰俯角
        originDot.setPitch(0);
        // 使原点与玩家有一点点距离
        originDot.add(originDot.getDirection().multiply(-0.3));
    }

    @Override
    public Location newLocation(double x, double y, double z) {
        return LocationUtils.rotateLocationAboutPoint(originDot.clone().add(-x, y, z), rotateAngle, originDot);
    }

}
