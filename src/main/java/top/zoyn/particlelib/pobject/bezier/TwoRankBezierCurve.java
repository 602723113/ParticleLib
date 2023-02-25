package top.zoyn.particlelib.pobject.bezier;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.ParticleLib;
import top.zoyn.particlelib.pobject.ParticleObject;
import top.zoyn.particlelib.pobject.Playable;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示一条二阶贝塞尔曲线
 * <p>给定三点, 自动生成一条二阶贝塞尔曲线</p>
 *
 * @author Zoyn
 */
public class TwoRankBezierCurve extends ParticleObject implements Playable {

    private final List<Location> locations;
    private Location p0;
    private Location p1;
    private Location p2;
    private double step;
    private int currentSample = 0;

    public TwoRankBezierCurve(Location p0, Location p1, Location p2) {
        this(p0, p1, p2, 0.05);
    }

    /**
     * 构造一个三阶贝塞尔曲线
     *
     * @param p0   连续点
     * @param p1   控制点
     * @param p2   控制点
     * @param step 每个粒子的间隔(也即步长)
     */
    public TwoRankBezierCurve(Location p0, Location p1, Location p2, double step) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.step = step;
        locations = new ArrayList<>();

        resetLocations();
    }

    @Override
    public List<Location> calculateLocations() {
        resetLocations();
        return locations;
    }

    @Override
    public void show() {
        locations.forEach(loc -> {
            if (loc != null) {
                spawnParticle(loc);
            }
        });
    }

    @Override
    public void play() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // 进行关闭
                if (currentSample + 1 == locations.size()) {
                    cancel();
                    return;
                }
                currentSample++;

                spawnParticle(locations.get(currentSample));
            }
        }.runTaskTimer(ParticleLib.getInstance(), 0, getPeriod());
    }

    @Override
    public void playNextPoint() {
        if (currentSample + 1 == locations.size()) {
            currentSample = 0;
        }
        spawnParticle(locations.get(currentSample));
        currentSample++;
    }

    public Location getP0() {
        return p0;
    }

    public TwoRankBezierCurve setP0(Location p0) {
        this.p0 = p0;
        resetLocations();
        return this;
    }

    public Location getP1() {
        return p1;
    }

    public TwoRankBezierCurve setP1(Location p1) {
        this.p1 = p1;
        resetLocations();
        return this;
    }

    public Location getP2() {
        return p2;
    }

    public TwoRankBezierCurve setP2(Location p2) {
        this.p2 = p2;
        resetLocations();
        return this;
    }

    public double getStep() {
        return step;
    }

    public TwoRankBezierCurve setStep(double step) {
        this.step = step;
        resetLocations();
        return this;
    }

    /**
     * 重新计算贝塞尔曲线上的点
     */
    public void resetLocations() {
        locations.clear();
        // 算法
        // 算了我知道很蠢这个算法...
        for (double t = 0; t < 1; t += step) {
            Vector v1 = p1.clone().subtract(p0).toVector();
            Location t1 = p0.clone().add(v1.multiply(t));
            Vector v2 = p2.clone().subtract(p1).toVector();
            Location t2 = p1.clone().add(v2.multiply(t));

            Vector v3 = t2.clone().subtract(t1).toVector();
            Location destination = t1.clone().add(v3.multiply(t));
            locations.add(destination.clone());
        }
    }

}
