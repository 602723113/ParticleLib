package top.zoyn.particlelib.pobject;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.ParticleLib;

/**
 * 表示一个线
 *
 * @author Zoyn
 */
public class Line extends ParticleObject {

    private Vector vector;
    private Location loc1;
    private Location loc2;
    /**
     * 步长
     */
    private double step;
    /**
     * 向量长度
     */
    private double length;
    private BukkitTask task;
    /**
     * 特效周期
     */
    private long period;

    public Line(Location loc1, Location loc2) {
        this(loc1, loc2, 0.1);
    }

    /**
     * 构造一个线
     *
     * @param loc1 线的起点
     * @param loc2 线的终点
     * @param step 每个粒子之间的间隔 (也即步长)
     */
    public Line(Location loc1, Location loc2, double step) {
        this(loc1, loc2, step, 20L);
    }

    /**
     * 构造一个线
     *
     * @param loc1   线的起点
     * @param loc2   线的终点
     * @param step   每个粒子之间的间隔 (也即步长)
     * @param period 特效周期(如果需要可以使用)
     */
    public Line(Location loc1, Location loc2, double step, long period) {
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.step = step;
        this.period = period;

        // 对向量进行重置
        resetVector();
    }

    @Override
    public void show() {
        for (double i = 0; i < length; i += step) {
            Vector vectorTemp = vector.clone().multiply(i);
            loc1.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc1.clone().add(vectorTemp), 1);
        }
    }

    @Override
    public void alwaysShow() {
        turnOffTask();

        task = new BukkitRunnable() {
            @Override
            public void run() {
                show();
            }
        }.runTaskTimer(ParticleLib.getInstance(), 0L, period);
    }

    @Override
    public void alwaysShowAsync() {
        turnOffTask();

        task = new BukkitRunnable() {
            @Override
            public void run() {
                show();
            }
        }.runTaskTimerAsynchronously(ParticleLib.getInstance(), 0L, period);
    }

    @Override
    public void turnOffTask() {
        if (task != null) {
            task.cancel();
        }
    }

    public Location getLoc1() {
        return loc1;
    }

    public Line setLoc1(Location loc1) {
        this.loc1 = loc1;
        resetVector();
        return this;
    }

    public Location getLoc2() {
        return loc2;
    }

    public Line setLoc2(Location loc2) {
        this.loc2 = loc2;
        resetVector();
        return this;
    }

    public double getStep() {
        return step;
    }

    public Line setStep(double step) {
        this.step = step;
        resetVector();
        return this;
    }

    public long getPeriod() {
        return period;
    }

    public Line setPeriod(long period) {
        this.period = period;
        return this;
    }

    public void resetVector() {
        vector = loc2.clone().subtract(loc1).toVector();
        length = vector.length();
        vector.normalize();
    }
}
