package top.zoyn.particlelib.pobject;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.ParticleLib;
import top.zoyn.particlelib.utils.VectorUtils;

/**
 * 表示一个星星
 *
 * @author Zoyn
 */
public class Star extends ParticleObject implements Playable {

    private final double length;
    private double radius;
    private double step;
    private int currentSide = 1;
    private double currentStep = 0;

    private Vector changeableStart;
    private Location changableEnd;

    public Star(Location origin) {
        this(origin, 1, 0.05);
    }

    public Star(Location origin, double radius, double step) {
        setOrigin(origin);
        this.radius = radius;
        this.step = step;

        // 每条线的长度
        this.length = Math.sin(Math.toRadians(72)) * radius * 2;

        double x = radius * Math.cos(Math.toRadians(72));
        double z = radius * Math.sin(Math.toRadians(72));
        changeableStart = new Vector(radius * (Math.cos(Math.toRadians(72 * 3)) - x), 0, radius * (Math.sin(Math.toRadians(72 * 3)) - z));
        changeableStart.normalize();
        changableEnd = getOrigin().clone().add(x, 0, z);
    }

    @Override
    public void show() {
        double x = radius * Math.cos(Math.toRadians(72));
        double z = radius * Math.sin(Math.toRadians(72));

        double x2 = radius * Math.cos(Math.toRadians(72 * 3));
        double z2 = radius * Math.sin(Math.toRadians(72 * 3));

        Vector START = new Vector(x2 - x, 0, z2 - z);
        START.normalize();
        Location end = getOrigin().clone().add(x, 0, z);

        for (int i = 1; i <= 5; i++) {
            for (double j = 0; j < length; j += step) {
                Vector vectorTemp = START.clone().multiply(j);
                Location spawnLocation = end.clone().add(vectorTemp);

                spawnParticle(spawnLocation);
            }
            Vector vectorTemp = START.clone().multiply(length);
            end = end.clone().add(vectorTemp);

            VectorUtils.rotateAroundAxisY(START, 144);
        }
    }

    @Override
    public void play() {
        new BukkitRunnable() {
            // 转弧度制
            final double radians = Math.toRadians(72);
            final double x = radius * Math.cos(radians);
            final double z = radius * Math.sin(radians);
            Location end = getOrigin().clone().add(x, 0, z);
            final Vector START = new Vector(radius * (Math.cos(Math.toRadians(72 * 3)) - x), 0, radius * (Math.sin(Math.toRadians(72 * 3)) - z));

            @Override
            public void run() {
                // 进行关闭
                if (currentSide >= 6) {
                    cancel();
                    return;
                }
                if (currentStep > length) {
                    // 切换到下一条边开始
                    currentSide += 1;
                    currentStep = 0;

                    Vector vectorTemp = START.clone().multiply(length);
                    end = end.clone().add(vectorTemp);

                    VectorUtils.rotateAroundAxisY(START, 144);
                }
                Vector vectorTemp = START.clone().multiply(currentStep);
                Location spawnLocation = end.clone().add(vectorTemp);

                spawnParticle(spawnLocation);
                currentStep += step;
            }
        }.runTaskTimer(ParticleLib.getInstance(), 0, getPeriod());
    }

    @Override
    public void playNextPoint() {
        Vector vectorTemp = changeableStart.clone().multiply(currentStep);
        Location spawnLocation = changableEnd.clone().add(vectorTemp);

        spawnParticle(spawnLocation);
        currentStep += step;
        if (currentStep >= length) {
            // 切换到下一条边开始
            currentSide += 1;
            currentStep = 0;

            vectorTemp = changeableStart.clone().multiply(length);
            changableEnd = changableEnd.clone().add(vectorTemp);

            VectorUtils.rotateAroundAxisY(changeableStart, 144);
        }

        // 重置
        if (currentSide >= 6) {
            currentSide = 1;
            currentStep = 0;

            double x = radius * Math.cos(Math.toRadians(72));
            double z = radius * Math.sin(Math.toRadians(72));
            changeableStart = new Vector(radius * (Math.cos(Math.toRadians(72 * 3)) - x), 0, radius * (Math.sin(Math.toRadians(72 * 3)) - z));
            changeableStart.normalize();
            changableEnd = getOrigin().clone().add(x, 0, z);
        }
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }
}
