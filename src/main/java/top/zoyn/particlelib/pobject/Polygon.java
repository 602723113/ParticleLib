package top.zoyn.particlelib.pobject;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.ParticleLib;
import top.zoyn.particlelib.utils.VectorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示一个多边形
 */
public class Polygon extends ParticleObject implements Playable {

    private final List<Location> locations;
    /**
     * 边数
     */
    private int side;
    private double step;
    private Vector currentVector;
    private int currentLoc = 0;
    private double length;
    private double currentStep = 0;

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
    public List<Location> calculateLocations() {
        List<Location> points = Lists.newArrayList();
        List<Location> temp = Lists.newArrayList();

        for (double angle = 0; angle <= 360; angle += 360D / side) {
            double radians = Math.toRadians(angle);
            double x = Math.cos(radians);
            double z = Math.sin(radians);

            temp.add(getOrigin().clone().add(x, 0, z));
        }
        for (int i = 0; i < temp.size(); i++) {
            if (i + 1 == temp.size()) {
                Vector vectorAB = temp.get(i).clone().subtract(temp.get(0)).toVector();
                double vectorLength = vectorAB.length();
                vectorAB.normalize();
                for (double j = 0; j < vectorLength; j += step) {
                    points.add(temp.get(0).clone().add(vectorAB.clone().multiply(j)));
                }
                break;
            }

            Vector vectorAB = temp.get(i + 1).clone().subtract(temp.get(i)).toVector();
            double vectorLength = vectorAB.length();
            vectorAB.normalize();
            for (double j = 0; j < vectorLength; j += step) {
                points.add(temp.get(i).clone().add(vectorAB.clone().multiply(j)));
            }
        }
        // 做一个对 Matrix 和 Increment 的兼容
        return points.stream().map(location -> {
            Location showLocation = location;
            if (hasMatrix()) {
                Vector v = new Vector(location.getX() - getOrigin().getX(), location.getY() - getOrigin().getY(), location.getZ() - getOrigin().getZ());
                Vector changed = getMatrix().applyVector(v);

                showLocation = getOrigin().clone().add(changed);
            }

            showLocation.add(getIncrementX(), getIncrementY(), getIncrementZ());
            return showLocation;
        }).collect(Collectors.toList());
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

    @Override
    public void play() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Vector vectorTemp = currentVector.clone().normalize().multiply(currentStep);
                spawnParticle(locations.get(currentLoc).clone().add(vectorTemp));

                // 重置
                if (currentStep > length) {
                    currentStep = 0D;
                    currentVector = VectorUtils.rotateAroundAxisY(currentVector, 360D / side);
                    currentLoc++;
                }
                // 在此处进行退出
                if (currentLoc == side) {
                    cancel();
                    return;
                }
                currentStep += step;
            }
        }.runTaskTimer(ParticleLib.getInstance(), 0, getPeriod());
    }

    @Override
    public void playNextPoint() {
        Vector vectorTemp = currentVector.clone().normalize().multiply(currentStep);
        spawnParticle(locations.get(currentLoc).clone().add(vectorTemp));

        // 重置
        if (currentStep > length) {
            currentStep = 0D;
            currentVector = VectorUtils.rotateAroundAxisY(currentVector, 360D / side);
            currentLoc++;
        }
        if (currentLoc == side) {
            currentLoc = 0;
        }
        currentStep += step;
    }

    public void resetLocations() {
        locations.clear();

        for (double angle = 0; angle <= 360; angle += 360D / side) {
            double radians = Math.toRadians(angle);
            double x = Math.cos(radians);
            double z = Math.sin(radians);

            locations.add(getOrigin().clone().add(x, 0, z));
        }

        currentVector = locations.get(1).clone().subtract(locations.get(0)).toVector();
        length = currentVector.length();
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
