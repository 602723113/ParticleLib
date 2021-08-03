package top.zoyn.particlelib.pobject;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import top.zoyn.particlelib.ParticleLib;

/**
 * 表示一个弧
 *
 * @author Zoyn IceCold
 */
public class Arc extends ParticleObject implements Playable {

    private final boolean isDone = true;
    private double angle;
    private double radius;
    private double step;
    private double currentAngle = 0D;
    private Color color;

    public Arc(Location origin) {
        this(origin, 30D);
    }

    public Arc(Location origin, double angle) {
        this(origin, angle, 1D);
    }

    public Arc(Location origin, double angle, double radius) {
        this(origin, angle, radius, 1);
    }


    /**
     * 构造一个弧
     *
     * @param origin 弧所在的圆的圆点
     * @param angle  弧所占的角度
     * @param radius 弧所在的圆的半径
     * @param step   每个粒子的间隔(也即步长)
     */
    public Arc(Location origin, double angle, double radius, double step) {
        this(origin, angle, radius, step, 20L);
    }

    /**
     * 构造一个弧
     *
     * @param origin 弧所在的圆的圆点
     * @param angle  弧所占的角度
     * @param radius 弧所在的圆的半径
     * @param step   每个粒子的间隔(也即步长)
     * @param period 特效周期(如果需要可以使用)
     */
    public Arc(Location origin, double angle, double radius, double step, long period) {
        setOrigin(origin);
        this.angle = angle;
        this.radius = radius;
        this.step = step;
        setPeriod(period);
    }

    /**
     * 构造一个彩色弧
     *
     * @param origin 弧所在的圆的圆点
     * @param angle  弧所占的角度
     * @param radius 弧所在的圆的半径
     * @param step   每个粒子的间隔(也即步长)
     * @param period 特效周期(如果需要可以使用)
     * @param color 颜色
     */
    public Arc(Location origin, double angle, double radius, double step, long period,Color color) {
        setOrigin(origin);
        this.angle = angle;
        this.radius = radius;
        this.step = step;
        setPeriod(period);
        this.color = color;
        setParticle(Particle.REDSTONE);
    }

    @Override
    public void show() {
        for (int i = 0; i < angle; i += step) {
            double radians = Math.toRadians(i);
            double x = radius * Math.cos(radians);
            double z = radius * Math.sin(radians);
            if (color != null){
                spawnParticle(getOrigin().clone().add(x, 0, z),color);
            }else {
                spawnParticle(getOrigin().clone().add(x, 0, z));
            }
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

                if (color != null){
                    spawnParticle(getOrigin().clone().add(x, 0, z),color);
                }else {
                    spawnParticle(getOrigin().clone().add(x, 0, z));
                }
            }
        }.runTaskTimer(ParticleLib.getInstance(), 0, getPeriod());
    }

    @Override
    public void playNextPoint() {
        currentAngle += step;
        double radians = Math.toRadians(currentAngle);
        double x = radius * Math.cos(radians);
        double z = radius * Math.sin(radians);

        if (color != null){
            spawnParticle(getOrigin().clone().add(x, 0, z),color);
        }else {
            spawnParticle(getOrigin().clone().add(x, 0, z));
        }

        // 进行重置
        if (currentAngle > angle) {
            currentAngle = 0D;
        }
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

    public Color getColor() {
        return color;
    }

    public Arc setColor(Color color) {
        this.color = color;
        setParticle(Particle.REDSTONE);
        return this;
    }
}
