package top.zoyn.particlelib.pobject;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示一个多边形
 */
public class Polygon extends ParticleObject {

    private final List<Location> locations;
    /**
     * 边数
     */
    private int side;
    private double step;


    /**
     * 构造一个多边形
     *
     * @param side   边数
     * @param origin 原点
     */
    public Polygon(int side, Location origin) {
        this(side, origin, 0.02);
    }

    public Polygon(int side, Location origin, double step) {
        if (side <= 2) {
            throw new IllegalArgumentException("边数不可为小于或等于2的数!");
        }
        this.side = side;
        setOrigin(origin);
        this.step = step;

        this.locations = new ArrayList<>();
        resetLocations();
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
        resetLocations();
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
        resetLocations();
    }

    @Override
    public void show() {
        if (locations.isEmpty()) {
            return;
        }

        for (int i = 0; i < locations.size(); i++) {
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
            double x = Math.cos(radians);
            double z = Math.sin(radians);

            locations.add(getOrigin().clone().add(x, 0, z));
        }
    }

    /**
     * 此方法只用于本 Polygon
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
