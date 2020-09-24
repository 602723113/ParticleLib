package top.zoyn.particlelib.pobject;

import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.function.Function;

public class ParametricEquationRenderer extends ParticleObject {

    private final Function<Double, Double> xFunction;
    private final Function<Double, Double> yFunction;
    private Location origin;
    private double minT;
    private double maxT;
    private double dt;

    public ParametricEquationRenderer(Location origin, Function<Double, Double> xFunction, Function<Double, Double> yFunction) {
        this(origin, xFunction, yFunction, 0D, 360D);
    }

    public ParametricEquationRenderer(Location origin, Function<Double, Double> xFunction, Function<Double, Double> yFunction, double minT, double maxT) {
        this(origin, xFunction, yFunction, minT, maxT, 1D);
    }

    public ParametricEquationRenderer(Location origin, Function<Double, Double> xFunction, Function<Double, Double> yFunction, double minT, double maxT, double dT) {
        this.origin = origin;
        this.xFunction = xFunction;
        this.yFunction = yFunction;
        this.minT = minT;
        this.maxT = maxT;
        this.dt = dT;
    }

    @Override
    public void show() {
        for (double t = minT; t < maxT; t += dt) {
            double x = xFunction.apply(t);
            double y = yFunction.apply(t);

            origin.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, origin.clone().add(x, y, 0), 1);
        }
    }

    public Location getOrigin() {
        return origin;
    }

    public ParametricEquationRenderer setOrigin(Location origin) {
        this.origin = origin;
        return this;
    }

    public double getMinT() {
        return minT;
    }

    public ParametricEquationRenderer setMinT(double minT) {
        this.minT = minT;
        return this;
    }

    public double getMaxT() {
        return maxT;
    }

    public ParametricEquationRenderer setMaxT(double maxT) {
        this.maxT = maxT;
        return this;
    }

    public double getDt() {
        return dt;
    }

    public ParametricEquationRenderer setDt(double dt) {
        this.dt = dt;
        return this;
    }
}
