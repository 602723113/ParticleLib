package top.zoyn.particlelib.pobject;

import org.bukkit.Location;

/**
 * 表示一个圆
 *
 * @author Zoyn IceCold
 */
public class Circle extends Arc {

    public Circle(Location origin) {
        this(origin, 1);
    }

    public Circle(Location origin, double radius) {
        this(origin, radius, 1);
    }

    /**
     * 构造一个圆
     *
     * @param origin 圆的圆点
     * @param radius 圆的半径
     * @param step   每个粒子的间隔(也即步长)
     */
    public Circle(Location origin, double radius, double step) {
        this(origin, radius, step, 20L);
    }

    /**
     * 构造一个圆
     *
     * @param origin 圆的圆点
     * @param radius 圆的半径
     * @param step   每个粒子的间隔(也即步长)
     * @param period 特效周期(如果需要可以使用)
     */
    public Circle(Location origin, double radius, double step, long period) {
        // Circle只需要控制这个fullArc就可以满足所有的要求
        super(origin, 360D, radius, step, period);
    }

}
