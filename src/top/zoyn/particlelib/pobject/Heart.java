package top.zoyn.particlelib.pobject;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

/**
 * 表示一颗心
 *
 * @author Zoyn
 */
public class Heart extends ParticleObject {

    private double xScaleRate;
    private double yScaleRate;

    /**
     * 构造一个小心心
     *
     * @param origin 原点
     */
    public Heart(Location origin) {
        this(1, 1, origin);
    }

    /**
     * 构造一个心形线
     *
     * @param xScaleRate X轴缩放比率
     * @param yScaleRate Y轴缩放比率
     * @param origin 原点
     */
    public Heart(double xScaleRate, double yScaleRate, Location origin) {
        this.xScaleRate = xScaleRate;
        this.yScaleRate = yScaleRate;
        setOrigin(origin);
    }

    public double getXScaleRate() {
        return xScaleRate;
    }

    public void setXScaleRate(double xScaleRate) {
        this.xScaleRate = xScaleRate;
    }

    public double getYScaleRate() {
        return yScaleRate;
    }

    public void setYScaleRate(double yScaleRate) {
        this.yScaleRate = yScaleRate;
    }

    @Override
    public void show() {
        for (double t = -1.0D; t <= 1.0D; t += 0.001D) {
            double x = xScaleRate * Math.sin(t) * Math.cos(t) * Math.log(Math.abs(t));
            double y = yScaleRate * Math.sqrt(Math.abs(t)) * Math.cos(t);

            spawnParticle(getOrigin().clone().add(x, 0, y));
        }
//        for (double t = 0.0D; t < 360.0D; t++) {
//            double x = xScaleRate * 16 * Math.pow(Math.sin(t), 3);
//            double z = yScaleRate * 13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t);
//
//            spawnParticle(getOrigin().clone().add(x, 0, z));
//        }
    }

}
