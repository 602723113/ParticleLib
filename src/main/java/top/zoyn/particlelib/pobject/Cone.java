package top.zoyn.particlelib.pobject;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.ParticleLib;
import top.zoyn.particlelib.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cone extends ParticleObject implements Playable {

    /**
     * 黄金角度 约等于137.5度
     */
    private final double phi = Math.PI * (3D - Math.sqrt(5));
    private final List<Location> locations;
    private int sample;
    private double radius;
    private final double height;
    private int currentSample = 0;

    public Cone(Location origin) {
        this(origin, 50, 1.5, 3);
    }

    /**
     * 构造一个圆锥
     *
     * @param origin 圆锥底面圆的原点
     * @param sample 样本点个数(粒子的数量)
     * @param radius 球的半径
     */
    public Cone(Location origin, int sample, double radius, double height) {
        setOrigin(origin);
        this.sample = sample;
        this.radius = radius;
        this.height = height;

        locations = new ArrayList<>();
        resetLocations();
    }

    @Override
    public List<Location> calculateLocations() {
        List<Location> points = Lists.newArrayList();

        List<Double> indices = MathUtils.arange(0, sample, 1);
        List<Double> phis = Lists.newArrayList();
        List<Double> thetas = Lists.newArrayList();
        for (double indice : indices) {
            double phi = Math.acos(1 - 2 * indice / sample);
            double theta = Math.PI * (1 + Math.sqrt(5)) * indice;
            phis.add(phi);
            thetas.add(theta);
        }

        for (int i = 0; i < sample; i++) {
            double theta = thetas.get(i);
            double phi = phis.get(i);
            double x = radius * Math.cos(theta) * Math.sin(phi);
            double y = height * -Math.sin(phi);
            double z = radius * Math.sin(theta) * Math.sin(phi);

            points.add(getOrigin().clone().add(x, y, z));
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
        // 重置
        if (currentSample + 1 == locations.size()) {
            currentSample = 0;
        }
        spawnParticle(locations.get(currentSample));
        currentSample++;
    }

    public int getSample() {
        return sample;
    }

    public Cone setSample(int sample) {
        this.sample = sample;
        resetLocations();
        return this;
    }

    public double getRadius() {
        return radius;
    }

    public Cone setRadius(double radius) {
        this.radius = radius;
        resetLocations();
        return this;
    }

    public void resetLocations() {
        locations.clear();

        List<Double> indices = MathUtils.arange(0, sample, 1);
        List<Double> phis = Lists.newArrayList();
        List<Double> thetas = Lists.newArrayList();
        for (double indice : indices) {
            double phi = Math.acos(1 - 2 * indice / sample);
            double theta = Math.PI * (1 + Math.sqrt(5)) * indice;
            phis.add(phi);
            thetas.add(theta);
        }

        for (int i = 0; i < sample; i++) {
            double theta = thetas.get(i);
            double phi = phis.get(i);
            double x = radius * Math.cos(theta) * Math.sin(phi);
            double y = height * -Math.sin(phi);
            double z = radius * Math.sin(theta) * Math.sin(phi);

            locations.add(getOrigin().clone().add(x, y, z));
        }
    }
}
