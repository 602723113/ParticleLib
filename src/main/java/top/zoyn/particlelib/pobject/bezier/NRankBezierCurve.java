package top.zoyn.particlelib.pobject.bezier;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import top.zoyn.particlelib.pobject.ParticleObject;

import java.util.Arrays;
import java.util.List;

/**
 * 表示一条n阶贝塞尔曲线
 * <p>给定 n + 1 点, 绘制一条平滑的曲线</p>
 *
 * @author Zoyn
 */
public class NRankBezierCurve extends ParticleObject {

    /**
     * 用于保存将要播放的粒子的点位
     */
    private final List<Location> points = Lists.newLinkedList();
    private final double step;
    /**
     * 用于计算贝塞尔曲线上的点
     */
    private final List<Location> locations;

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
        resetLocation();
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
    public void show() {
        points.forEach(loc -> {
            if (loc != null) {
                spawnParticle(loc);
            }
        });
    }

    public void resetLocation() {
        points.clear();

        for (double t = 0; t < 1; t += step) {
            Location location = calculateCurve(locations, t);
            points.add(location);
        }
    }
}
