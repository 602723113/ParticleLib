package top.zoyn.particlelib.pobject;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示一个N棱锥特效
 *
 * @author Zoyn
 */
public class Pyramid extends ParticleObject {

    private final List<Location> locations;
    private int side;
    private double height;
    private double step;
    private double radius;
    private Location upLoc;

    public Pyramid(Location origin, int side) {
        this(origin, side, 1, 1);
    }

    public Pyramid(Location origin, int side, double radius, double height) {
        this(origin, side, radius, height, 0.02);
    }

    /**
     * 表示一个棱锥特效
     *
     * @param origin 棱锥底面中心点
     * @param side   棱的个数
     * @param radius 底面半径, 中心点到任意一个角的长度
     * @param height 底面中心点到最上方顶点的长度
     * @param step   粒子的间距
     */
    public Pyramid(Location origin, int side, double radius, double height, double step) {
        if (side <= 2) {
            throw new IllegalArgumentException("边数不可为小于或等于2的数!");
        }
        this.side = side;
        this.height = height;
        this.step = step;
        this.radius = radius;

        this.locations = new ArrayList<>();
        setOrigin(origin);
    }

    public int getSide() {
        return side;
    }

    public Pyramid setSide(int side) {
        this.side = side;
        resetLocations();
        return this;

    }

    public double getHeight() {
        return height;
    }

    public Pyramid setHeight(double height) {
        this.height = height;
        upLoc = getOrigin().clone().add(0, height, 0);
        resetLocations();
        return this;
    }

    public double getStep() {
        return step;
    }

    public Pyramid setStep(double step) {
        this.step = step;
        resetLocations();
        return this;
    }

    public double getRadius() {
        return radius;
    }

    public Pyramid setRadius(double radius) {
        this.radius = radius;
        resetLocations();
        return this;
    }

    @Override
    public ParticleObject setOrigin(Location origin) {
        super.setOrigin(origin);
        // 重置最上方的 Loc
        upLoc = origin.clone().add(0, height, 0);

        resetLocations();
        return this;
    }

    @Override
    public List<Location> calculateLocations() {
        resetLocations();
        return locations;
    }

    @Override
    public void show() {
        if (locations.isEmpty()) {
            return;
        }

        for (int i = 0; i < locations.size(); i++) {
            // 棱
            buildLine(upLoc, locations.get(i), step);
            // 底面
            if (i + 1 == locations.size()) {
                buildLine(locations.get(i), locations.get(0), step);
                break;
            }
            buildLine(locations.get(i), locations.get(i + 1), step);
        }
    }

    public void resetLocations() {
        locations.clear();

        for (double angle = 0; angle <= 360; angle += 360D / side) {
            double radians = Math.toRadians(angle);
            double x = radius * Math.cos(radians);
            double z = radius * Math.sin(radians);

            locations.add(getOrigin().clone().add(x, 0, z));
        }
    }

    /**
     * 此方法只用于 Pyramid
     *
     * @param locA 点A
     * @param locB 点B
     * @param step 步长
     */
    private void buildLine(Location locA, Location locB, double step) {
        Vector vectorAB = locB.clone().subtract(locA).toVector();
        double vectorLength = vectorAB.length();
        vectorAB.normalize();
        for (double i = 0; i < vectorLength; i += step) {
            spawnParticle(locA.clone().add(vectorAB.clone().multiply(i)));
        }
    }
}
