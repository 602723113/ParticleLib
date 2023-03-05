package top.zoyn.particlelib.pobject;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.utils.VectorUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示一个翅膀
 *
 * @author Zoyn
 */
public class Wing extends ParticleObject {

    /**
     * 用于缓存所有的点的向量
     */
    private final List<Vector> vectors;
    /**
     * 翅膀的图案
     */
    private List<String> pattern;
    /**
     * 粒子之间的距离
     */
    private double interval;
    /**
     * 最小起始旋转角
     */
    private double minRotAngle;
    /**
     * 最大旋转角
     */
    private double maxRotAngle;
    /**
     * 翅膀是否要进行旋转
     */
    private boolean swing;

    private double currentAngle;
    private boolean increase;

    public Wing(Location origin, List<String> pattern) {
        this(origin, pattern, 30D, 60D, 0.2D);
    }

    public Wing(Location origin, List<String> pattern, double minRotAngle, double maxRotAngle, double interval) {
        setOrigin(origin);
        this.pattern = pattern;
        this.interval = interval;
        this.minRotAngle = -minRotAngle;
        this.maxRotAngle = -maxRotAngle;

        currentAngle = -minRotAngle;
        increase = false;
        vectors = Lists.newArrayList();
        swing = true;
        resetWing();
    }

    @Override
    public List<Location> calculateLocations() {
        resetWing();

        List<Location> points = Lists.newArrayList();
        for (Vector vector : vectors) {
            if (getEntity() != null) {
                points.add(getOrigin().clone().add(VectorUtils.rotateVector(vector, getOrigin().getYaw() - 90 + (float) currentAngle, 0F)));
                points.add(getOrigin().clone().add(VectorUtils.rotateVector(vector.clone().setX(-vector.getX()), getOrigin().getYaw() - 90 - (float) currentAngle, 0F)));
                continue;
            }
            points.add(getOrigin().clone().add(VectorUtils.rotateVector(vector, (float) currentAngle, 0F)));
            points.add(getOrigin().clone().add(VectorUtils.rotateVector(vector.clone().setX(-vector.getX()), -(float) currentAngle, 0F)));
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
        for (Vector vector : vectors) {
            if (getEntity() != null) {
                spawnParticle(getOrigin().clone().add(VectorUtils.rotateVector(vector, getOrigin().getYaw() - 90 + (float) currentAngle, 0F)));
                spawnParticle(getOrigin().clone().add(VectorUtils.rotateVector(vector.clone().setX(-vector.getX()), getOrigin().getYaw() - 90 - (float) currentAngle, 0F)));
                continue;
            }
            spawnParticle(getOrigin().clone().add(VectorUtils.rotateVector(vector, (float) currentAngle, 0F)));
            spawnParticle(getOrigin().clone().add(VectorUtils.rotateVector(vector.clone().setX(-vector.getX()), -(float) currentAngle, 0F)));
        }
        if (!swing) {
            return;
        }
        if (!increase) {
            currentAngle--;
        } else {
            currentAngle++;
        }

        if (currentAngle >= minRotAngle) {
            increase = false;
        }
        if (currentAngle <= maxRotAngle) {
            increase = true;
        }
    }

    /**
     * 利用图案来计算出每个粒子的向量
     */
    public void resetWing() {
        vectors.clear();

        for (int i = 0; i < pattern.size(); i++) {
            String line = pattern.get(i);
            char[] chars = line.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char c = chars[j];
                if (!Character.isWhitespace(c)) {
                    double x = interval * (j + 1);
                    double y = interval * (pattern.size() - i);
                    Vector vector = new Vector(x, y, 0);
                    vectors.add(vector);
                }
            }
        }
    }

    public List<String> getPattern() {
        return pattern;
    }

    public void setPattern(List<String> pattern) {
        this.pattern = pattern;
        resetWing();
    }

    public double getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public double getMinRotAngle() {
        return minRotAngle;
    }

    public void setMinRotAngle(double minRotAngle) {
        this.minRotAngle = minRotAngle;
    }

    public double getMaxRotAngle() {
        return maxRotAngle;
    }

    public void setMaxRotAngle(double maxRotAngle) {
        this.maxRotAngle = maxRotAngle;
    }

    public boolean isSwing() {
        return swing;
    }

    public void setSwing(boolean swing) {
        this.swing = swing;
    }
}
