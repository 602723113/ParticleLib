package top.zoyn.particlelib.pobject.equation;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import top.zoyn.particlelib.ParticleLib;
import top.zoyn.particlelib.pobject.ParticleObject;
import top.zoyn.particlelib.pobject.Playable;

import java.util.function.Function;

/**
 * 表示一个普通方程渲染器
 *
 * @author Zoyn
 */
public class GeneralEquationRenderer extends ParticleObject implements Playable {

    private final Function<Double, Double> function;
    private double minX;
    private double maxX;
    private double dx;
    private double currentX;

    public GeneralEquationRenderer(Location origin, Function<Double, Double> function) {
        this(origin, function, -5D, 5D);
    }

    public GeneralEquationRenderer(Location origin, Function<Double, Double> function, double minX, double maxX) {
        this(origin, function, minX, maxX, 0.1);
    }

    public GeneralEquationRenderer(Location origin, Function<Double, Double> function, double minX, double maxX, double dx) {
        setOrigin(origin);
        this.function = function;
        this.minX = minX;
        this.maxX = maxX;
        this.dx = dx;
    }

    @Override
    public void show() {
        for (double x = minX; x < maxX; x += dx) {
            spawnParticle(getOrigin().clone().add(x, function.apply(x), 0));
        }
    }

    @Override
    public void play() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // 进行关闭
                if (currentX > maxX) {
                    cancel();
                    return;
                }
                currentX += dx;
                spawnParticle(getOrigin().clone().add(currentX, function.apply(currentX), 0));
            }
        }.runTaskTimer(ParticleLib.getInstance(), 0, getPeriod());
    }

    @Override
    public void playNextPoint() {
        // 进行关闭
        if (currentX > maxX) {
            currentX = minX;
        }
        currentX += dx;
        spawnParticle(getOrigin().clone().add(currentX, function.apply(currentX), 0));
    }

    public double getMinX() {
        return minX;
    }

    public GeneralEquationRenderer setMinX(double minX) {
        this.minX = minX;
        return this;
    }

    public double getMaxX() {
        return maxX;
    }

    public GeneralEquationRenderer setMaxX(double maxX) {
        this.maxX = maxX;
        return this;
    }

    public double getDx() {
        return dx;
    }

    public GeneralEquationRenderer setDx(double dx) {
        this.dx = dx;
        return this;
    }
}
