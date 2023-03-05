package top.zoyn.particlelib.pobject;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.ParticleLib;

import java.util.List;

/**
 * 表示一个弧
 *
 * @author Zoyn IceCold
 */
public class Arc extends ParticleObject implements Playable {

    private double startAngle;
    private double angle;
    private double radius;
    private double step;
    private double currentAngle = 0D;

    public Arc(Location origin) {
        this(origin, 60D);
    }

    public Arc(Location origin, double angle) {
        this(origin, 0D, angle);
    }

    public Arc(Location origin, double startAngle, double angle) {
        this(origin, startAngle, angle, 1);
    }

    public Arc(Location origin, double startAngle, double angle, double radius) {
        this(origin, startAngle, angle, radius, 1);
    }

    /**
     * 构造一个弧
     *
     * @param origin     弧所在的圆的圆点
     * @param startAngle 弧开始的角度
     * @param angle      弧所占的角度
     * @param radius     弧所在的圆的半径
     * @param step       每个粒子的间隔(也即步长)
     */
    public Arc(Location origin, double startAngle, double angle, double radius, double step) {
        this(origin, startAngle, angle, radius, step, 20L);
    }

    /**
     * 构造一个弧
     *
     * @param origin     弧所在的圆的圆点
     * @param startAngle 弧开始的角度
     * @param angle      弧所占的角度
     * @param radius     弧所在的圆的半径
     * @param step       每个粒子的间隔(也即步长)
     * @param period     特效周期(如果需要可以使用)
     */
    public Arc(Location origin, double startAngle, double angle, double radius, double step, long period) {
        setOrigin(origin);
        this.startAngle = startAngle;
        this.angle = angle;
        this.radius = radius;
        this.step = step;
        setPeriod(period);
    }

    @Override
    public List<Location> calculateLocations() {
        List<Location> points = Lists.newArrayList();
        for (double i = startAngle; i < angle; i += step) {
            double radians = Math.toRadians(i);
            double x = radius * Math.cos(radians);
            double z = radius * Math.sin(radians);

            Location showLocation = getOrigin().clone().add(x, 0, z);
            if (hasMatrix()) {
                Vector vector = new Vector(x, 0, z);
                Vector changed = getMatrix().applyVector(vector);

                showLocation = getOrigin().clone().add(changed);
            }

            showLocation.add(getIncrementX(), getIncrementY(), getIncrementZ());
            points.add(showLocation);
        }
        return points;
    }

    @Override
    public void show() {
        for (double i = startAngle; i < angle; i += step) {
            double radians = Math.toRadians(i);
            double x = radius * Math.cos(radians);
            double z = radius * Math.sin(radians);
            spawnParticle(getOrigin().clone().add(x, 0, z));
        }
    }

    @Override
    public void play() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // 进行关闭
                if (currentAngle > angle) {
                    cancel();
                    return;
                }
                currentAngle += step;
                double radians = Math.toRadians(currentAngle);
                double x = radius * Math.cos(radians);
                double z = radius * Math.sin(radians);

                spawnParticle(getOrigin().clone().add(x, 0, z));
            }
        }.runTaskTimer(ParticleLib.getInstance(), 0, getPeriod());
    }

    @Override
    public void playNextPoint() {
        currentAngle += step;
        double radians = Math.toRadians(currentAngle);
        double x = radius * Math.cos(radians);
        double z = radius * Math.sin(radians);

        spawnParticle(getOrigin().clone().add(x, 0, z));

        // 进行重置
        if (currentAngle > angle) {
            currentAngle = 0D;
        }
    }

    public double getStartAngle() {
        return startAngle;
    }

    public Arc setStartAngle(double startAngle) {
        this.startAngle = startAngle;
        return this;
    }

    public double getAngle() {
        return angle;
    }

    public Arc setAngle(double angle) {
        this.angle = angle;
        return this;
    }

    public double getRadius() {
        return radius;
    }

    public Arc setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public double getStep() {
        return step;
    }

    public Arc setStep(double step) {
        this.step = step;
        return this;
    }

}
