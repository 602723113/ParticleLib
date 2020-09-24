package top.zoyn.particlelib.pobject;

import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.function.Function;

public class PolarEquationRenderer extends ParticleObject {

    private final Function<Double, Double> function;
    private Location origin;
    private double minTheta;
    private double maxTheta;
    private double dTheta;

    public PolarEquationRenderer(Location origin, Function<Double, Double> function) {
        this(origin, function, 0D, 360D, 1D);
    }

    public PolarEquationRenderer(Location origin, Function<Double, Double> function, double minTheta, double maxTheta, double dTheta) {
        this.origin = origin;
        this.function = function;
        this.minTheta = minTheta;
        this.maxTheta = maxTheta;
        this.dTheta = dTheta;
    }

    @Override
    public void show() {
        for (double theta = minTheta; theta < maxTheta; theta += dTheta) {
            double x = function.apply(theta) * Math.cos(theta);
            double y = function.apply(theta) * Math.sin(theta);

            origin.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, origin.clone().add(x, y, 0), 1);
        }
    }

    public Location getOrigin() {
        return origin;
    }

    public PolarEquationRenderer setOrigin(Location origin) {
        this.origin = origin;
        return this;
    }

    public double getMinTheta() {
        return minTheta;
    }

    public PolarEquationRenderer setMinTheta(double minTheta) {
        this.minTheta = minTheta;
        return this;
    }

    public double getMaxTheta() {
        return maxTheta;
    }

    public PolarEquationRenderer setMaxTheta(double maxTheta) {
        this.maxTheta = maxTheta;
        return this;
    }

    public double getDTheta() {
        return dTheta;
    }

    public PolarEquationRenderer setDTheta(double dTheta) {
        this.dTheta = dTheta;
        return this;
    }


}
