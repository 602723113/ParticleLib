package top.zoyn.particlelib.utils.coordinate;

import org.bukkit.Location;

/**
 * 表示一个坐标器
 *
 * @author Zoyn
 */
public interface Coordinate {

    Location newLocation(double x, double y, double z);

}
