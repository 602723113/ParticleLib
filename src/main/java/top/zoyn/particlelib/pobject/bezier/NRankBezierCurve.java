package top.zoyn.particlelib.pobject.bezier;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import top.zoyn.particlelib.ParticleLib;
import top.zoyn.particlelib.pobject.ParticleObject;
import top.zoyn.particlelib.pobject.Playable;

import java.util.Arrays;
import java.util.List;

/**
 * 表示一条n阶贝塞尔曲线
 * <p>给定 n + 1 点, 绘制一条平滑的曲线</p>
 *
 * @author Zoyn
 */
public class NRankBezierCurve extends ParticleObject implements Playable {

    /**
     * 用于保存将要播放的粒子的点位
     */
    private final List<Location> points = Lists.newLinkedList();
    private final double step;
    /**
     * 用于计算贝塞尔曲线上的点
     */
    private final List<Location> locations;
    private int currentSample = 0;

    public NRankBezierCurve(Location... locations) {
        this(Arrays.asList(locations));
    }

    public NRankBezierCurve(List<Location> locations) {
        this(locations, 0.05D);
    }

    /**
     * 构造一个N阶贝塞尔曲线
     *
     * @param locations 所有的点
     * @param step      T的步进数
     */
    public NRankBezierCurve(List<Location> locations, double step) {
        this.locations = locations;
        this.step = step;
        resetLocations();
    }

    private static Location calculateCurve(List<Location> locList, double t) {
        if (locList.size() == 2) {
            return locList.get(0).clone().add(locList.get(1).clone().subtract(locList.get(0)).toVector().multiply(t));
        }

        List<Location> locListTemp = Lists.newArrayList();
        for (int i = 0; i < locList.size(); i++) {
            if (i + 1 == locList.size()) {
                break;
            }
            Location p0 = locList.get(i);
            Location p1 = locList.get(i + 1);

            // 降阶处理
            locListTemp.add(p0.clone().add(p1.clone().subtract(p0).toVector().multiply(t)));
        }
        return calculateCurve(locListTemp, t);
    }

    @Override
    public List<Location> calculateLocations() {
        resetLocations();
        return points;
    }

    @Override
    public void show() {
        points.forEach(loc -> {
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
                if (currentSample + 1 == points.size()) {
                    cancel();
                    return;
                }
                currentSample++;

                spawnParticle(points.get(currentSample));
            }
        }.runTaskTimer(ParticleLib.getInstance(), 0, getPeriod());
    }

    @Override
    public void playNextPoint() {
        if (currentSample + 1 == points.size()) {
            currentSample = 0;
        }
        spawnParticle(points.get(currentSample));
        currentSample++;
    }

    public void resetLocations() {
        points.clear();

        for (double t = 0; t < 1; t += step) {
            Location location = calculateCurve(locations, t);
            points.add(location);
        }
    }
}
