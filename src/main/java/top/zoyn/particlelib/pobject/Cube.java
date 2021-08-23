package top.zoyn.particlelib.pobject;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.utils.VectorUtils;

/**
 * 表示一个立方体特效
 *
 * @author Zoyn
 */
public class Cube extends ParticleObject {

    /**
     * 向上的向量
     */
    private static final Vector UP = new Vector(0, 1, 0).normalize();
    private final Location minLoc;
    private final Location maxLoc;
    private final double step;

    public Cube(Location minLoc, Location maxLoc) {
        this(minLoc, maxLoc, 0.2D);

    }

    /**
     * 构造一个立方体
     *
     * @param minLoc 一个点
     * @param maxLoc 另外一个点
     * @param step   绘制边框时的步进长度
     */
    public Cube(Location minLoc, Location maxLoc, double step) {
        this.minLoc = minLoc;
        this.maxLoc = maxLoc;
        this.step = step;
        if (minLoc.getWorld() != maxLoc.getWorld()) {
            throw new IllegalArgumentException("这两个坐标的所对应的世界不相同");
        }
    }

    @Override
    public void show() {
        // 获得最大最小的两个点
        double minX = Math.min(minLoc.getX(), maxLoc.getX());
        double minY = Math.min(minLoc.getY(), maxLoc.getY());
        double minZ = Math.min(minLoc.getZ(), maxLoc.getZ());

        double maxX = Math.max(minLoc.getX(), maxLoc.getX());
        double maxY = Math.max(minLoc.getY(), maxLoc.getY());
        double maxZ = Math.max(minLoc.getZ(), maxLoc.getZ());

        Location minLoc = new Location(this.minLoc.getWorld(), minX, minY, minZ);

        // 获得立方体的 长 宽 高
        double width = maxX - minX;
        double height = maxY - minY;
        double depth = maxZ - minZ;

        // 此处的 newOrigin是底部的四个点
        Location newOrigin = minLoc.clone().add(width, 0, 0);
        // 构造第一条边的向量
        Vector vector = newOrigin.clone().subtract(minLoc).toVector();
        newOrigin = minLoc;
        double length;
        vector.normalize();

        for (int i = 1; i <= 4; i++) {
            if (i % 2 == 0) {
                length = depth;
            } else {
                length = width;
            }

            // 4条高
            for (double j = 0; j < height; j += step) {
                spawnParticle(newOrigin.clone().add(UP.clone().multiply(j)));
            }

            // 第n条边
            for (double j = 0; j < length; j += step) {
                Location spawnLoc = newOrigin.clone().add(vector.clone().multiply(j));
                spawnParticle(spawnLoc);
                spawnParticle(spawnLoc.add(0, height, 0));
            }
            // 获取结束时的坐标
            newOrigin = newOrigin.clone().add(vector.clone().multiply(length));
            vector = VectorUtils.rotateAroundAxisY(vector, 90D);
        }
    }
}
