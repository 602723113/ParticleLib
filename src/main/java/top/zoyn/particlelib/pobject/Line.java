package top.zoyn.particlelib.pobject;

import com.google.common.collect.Lists;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.ParticleLib;

import java.util.List;

/**
 * 表示一条线
 *
 * @author Zoyn IceCold
 */
public class Line extends ParticleObject implements Playable {

    private Vector vector;
    private Location start;
    private Location end;
    /**
     * 步长
     */
    private double step;
    /**
     * 向量长度
     */
    private double length;
    private double currentStep = 0D;

    public Line(Location start, Location end) {
        this(start, end, 0.1);
    }

    /**
     * 构造一个线
     *
     * @param start 线的起点
     * @param end   线的终点
     * @param step  每个粒子之间的间隔 (也即步长)
     */
    public Line(Location start, Location end, double step) {
        this(start, end, step, 20L);
    }

    /**
     * 构造一个线
     *
     * @param start  线的起点
     * @param end    线的终点
     * @param step   每个粒子之间的间隔 (也即步长)
     * @param period 特效周期(如果需要可以使用)
     */
    public Line(Location start, Location end, double step, long period) {
        this.start = start;
        this.end = end;
        this.step = step;
        setPeriod(period);

        // 对向量进行重置
        resetVector();
    }

    public static void buildLine(Location locA, Location locB, double step, Particle particle) {
        Vector vectorAB = locB.clone().subtract(locA).toVector();
        double vectorLength = vectorAB.length();
        vectorAB.normalize();
        for (double i = 0; i < vectorLength; i += step) {
            locA.getWorld().spawnParticle(particle, locA.clone().add(vectorAB.clone().multiply(i)), 1);
        }
    }

    public static void buildLine(Location locA, Location locB, double step, Color color) {
        Vector vectorAB = locB.clone().subtract(locA).toVector();
        double vectorLength = vectorAB.length();
        vectorAB.normalize();
        for (double i = 0; i < vectorLength; i += step) {
            locA.getWorld().spawnParticle(Particle.REDSTONE, locA.clone().add(vectorAB.clone().multiply(i)), 1, 0, 0, 0, color);
        }
    }

    @Override
    public List<Location> calculateLocations() {
        List<Location> points = Lists.newArrayList();
        for (double i = 0; i < length; i += step) {
            Vector vectorTemp = vector.clone().multiply(i);
            points.add(start.clone().add(vectorTemp));
        }
        return points;
    }

    @Override
    public void show() {
        for (double i = 0; i < length; i += step) {
            Vector vectorTemp = vector.clone().multiply(i);
            spawnParticle(start.clone().add(vectorTemp));
        }
    }

    @Override
    public void play() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // 进行关闭
                if (currentStep > length) {
                    cancel();
                    return;
                }
                currentStep += step;
                Vector vectorTemp = vector.clone().multiply(currentStep);
                spawnParticle(start.clone().add(vectorTemp));
            }
        }.runTaskTimer(ParticleLib.getInstance(), 0, getPeriod());
    }

    @Override
    public void playNextPoint() {
        currentStep += step;
        Vector vectorTemp = vector.clone().multiply(currentStep);
        spawnParticle(start.clone().add(vectorTemp));

        if (currentStep > length) {
            currentStep = 0D;
        }
    }

    public Location getStart() {
        return start;
    }

    public Line setStart(Location start) {
        this.start = start;
        resetVector();
        return this;
    }

    public Location getEnd() {
        return end;
    }

    public Line setEnd(Location end) {
        this.end = end;
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

    public void resetVector() {
        vector = end.clone().subtract(start).toVector();
        length = vector.length();
        vector.normalize();
    }

}
