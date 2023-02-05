package top.zoyn.particlelib.pobject;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.utils.VectorUtils;

/**
 * 表示一个八角星
 *
 * @author Zoyn
 */
public class OctagonalStar extends ParticleObject {

    private double radius;
    private double step;

    public OctagonalStar(Location origin, double radius, double step) {
        setOrigin(origin);

        this.radius = radius;
        this.step = step;
    }

    @Override
    public void show() {
        double x = radius * Math.cos(Math.toRadians(45));
        double z = radius * Math.sin(Math.toRadians(45));

        double x2 = radius * Math.cos(Math.toRadians(45 * 3));
        double z2 = radius * Math.sin(Math.toRadians(45 * 3));

        Vector START = new Vector(x2 - x, 0, z2 - z);
        double length = START.length();
        START.normalize();
        Location end = getOrigin().clone().add(x, 0, z);

        for (int i = 1; i <= 8; i++) {
            for (double j = 0; j < length; j += step) {
                Vector vectorTemp = START.clone().multiply(j);
                Location spawnLocation = end.clone().add(vectorTemp);

                spawnParticle(spawnLocation);
            }
            Vector vectorTemp = START.clone().multiply(length);
            end = end.clone().add(vectorTemp);

            VectorUtils.rotateAroundAxisY(START, 135);
        }
    }

    public double getRadius() {
        return radius;
    }

    public OctagonalStar setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public double getStep() {
        return step;
    }

    public OctagonalStar setStep(double step) {
        this.step = step;
        return this;
    }
}
