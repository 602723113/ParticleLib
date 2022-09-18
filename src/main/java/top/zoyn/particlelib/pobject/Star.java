package top.zoyn.particlelib.pobject;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.utils.VectorUtils;

/**
 * 表示一个星星
 *
 * @author Zoyn
 */
public class Star extends ParticleObject {

    private final double radius;
    private final double step;

    public Star(Location origin) {
        this(origin, 1, 0.05);
    }

    public Star(Location origin, double radius, double step) {
        setOrigin(origin);
        this.radius = radius;
        this.step = step;
    }

    @Override
    public void show() {
        Vector v = new Vector(1, 0, 0);
        // 转弧度制
        double radians = Math.toRadians(72 * 2);
        double x = radius * Math.cos(radians);
        double y = 0D;
        double z = radius * Math.sin(radians);
        Location end = getOrigin().clone().add(x, y, z);

        double length = Math.cos(Math.toRadians(36)) * radius * 2;
        for (int i = 1; i <= 5; i++) {
            for (double j = 0; j < length; j += step) {
                Vector vectorTemp = v.clone().multiply(j);
                Location spawnLocation = end.clone().add(vectorTemp);

                spawnParticle(spawnLocation);
            }
            Vector vectorTemp = v.clone().multiply(length);
            end = end.clone().add(vectorTemp);

            VectorUtils.rotateAroundAxisY(v, -144);
        }
    }
}
