package top.zoyn.particlelib.pobject;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import top.zoyn.particlelib.ParticleLib;

public class Arc extends ParticleObject {

    private Location origin;
    private double angle;
    private double radius;
    private double step;
    private BukkitTask task;
    private long period;
    private boolean running = false;

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
        this.origin = origin;
        this.angle = angle;
        this.radius = radius;
        this.step = step;
        this.period = period;
    }

    @Override
    public void show() {
        for (int i = 0; i < angle; i += step) {
            double radians = Math.toRadians(i);
            double x = radius * Math.cos(radians);
            double z = radius * Math.sin(radians);

            origin.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, origin.clone().add(x, 0, z), 1);
        }
    }

    @Override
    public void alwaysShow() {
        turnOffTask();

        // 此处的延迟 2tick 是为了防止turnOffTask还没把特效给关闭时的缓冲
        Bukkit.getScheduler().runTaskLater(ParticleLib.getInstance(), () -> {
            running = true;
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!running) {
                        return;
                    }
                    show();
                }
            }.runTaskTimer(ParticleLib.getInstance(), 0L, period);

            setShowType(ShowType.ALWAYS_SHOW);
        }, 2L);
    }

    @Override
    public void alwaysShowAsync() {
        turnOffTask();

        // 此处的延迟 2tick 是为了防止turnOffTask还没把特效给关闭时的缓冲
        Bukkit.getScheduler().runTaskLater(ParticleLib.getInstance(), () -> {
            running = true;
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!running) {
                        return;
                    }
                    show();
                }
            }.runTaskTimerAsynchronously(ParticleLib.getInstance(), 0L, period);

            setShowType(ShowType.ALWAYS_SHOW_ASYNC);
        }, 2L);
    }

    @Override
    public void turnOffTask() {
        if (task != null) {
            running = false;
            task.cancel();
            setShowType(ShowType.NONE);
        }
    }

    public Location getOrigin() {
        return origin;
    }

    public Arc setOrigin(Location origin) {
        this.origin = origin;
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

    public long getPeriod() {
        return period;
    }

    public Arc setPeriod(long period) {
        this.period = period;
        return this;
    }
}
