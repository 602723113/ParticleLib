package top.zoyn.particlelib.pobject;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.ParticleLib;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示一个实心圆
 *
 * @author Zoyn
 */
public class FilledCircle extends ParticleObject implements Playable {

    private final double radius;
    // 粒子数量
    private final int sample;
    // 动画中当前的第 currentCount 个粒子
    private int currentCount;

    /**
     * 构造一个实心圆
     *
     * @param origin 原点坐标
     * @param radius 半径大小
     * @param sample 粒子数量
     */
    public FilledCircle(Location origin, double radius, int sample) {
        setOrigin(origin);
        this.radius = radius;
        this.sample = sample;

        this.currentCount = 0;
    }

    @Override
    public void show() {
        for (int i = 0; i < sample; i++) {
            double indices = i + 0.5;
            double r = Math.sqrt(indices / sample);
            double theta = Math.PI * (1 + Math.sqrt(5)) * indices;
            double x = radius * r * Math.cos(theta);
            double z = radius * r * Math.sin(theta);

            Location spawnLocation = getOrigin().clone().add(x, 0, z);
            spawnParticle(spawnLocation);
        }
    }

    @Override
    public List<Location> calculateLocations() {
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < sample; i++) {
            double indices = i + 0.5;
            double r = Math.sqrt(indices / sample);
            double theta = Math.PI * (1 + Math.sqrt(5)) * indices;
            double x = radius * r * Math.cos(theta);
            double z = radius * r * Math.sin(theta);

            Location showLocation = getOrigin().clone().add(x, 0, z);
            if (hasMatrix()) {
                Vector vector = new Vector(x, 0 ,z);
                Vector changed = getMatrix().applyVector(vector);

                showLocation = getOrigin().clone().add(changed);
            }

            showLocation.add(getIncrementX(), getIncrementY(), getIncrementZ());
            locations.add(showLocation);
//            Location spawnLocation = getOrigin().clone().add(x, 0, z);
//            locations.add(spawnLocation);
        }
        return locations;
    }

    /**
     * 获得实心圆中所有点的Location
     *
     * @param origin 原点
     * @param count  个数
     * @return 粒子播放的点
     */
    public List<Location> calculateLocations(Location origin, long count) {
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double indices = i + 0.5;
            double r = Math.sqrt(indices / count);
            double theta = Math.PI * (1 + Math.sqrt(5)) * indices;
            double x = radius * r * Math.cos(theta);
            double z = radius * r * Math.sin(theta);

            Location spawnLocation = origin.clone().add(x, 0, z);
            locations.add(spawnLocation);
        }
        return locations;
    }

    @Override
    public void play() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (currentCount > sample) {
                    cancel();
                    return;
                }
                currentCount++;
                double indices = currentCount + 0.5;
                double r = Math.sqrt(indices / sample);
                double theta = Math.PI * (1 + Math.sqrt(5)) * indices;
                double x = radius * r * Math.cos(theta);
                double z = radius * r * Math.sin(theta);

                spawnParticle(getOrigin().clone().add(x, 0, z));
            }
        }.runTaskTimer(ParticleLib.getInstance(), 0, getPeriod());
    }

    @Override
    public void playNextPoint() {
        currentCount++;
        double indices = currentCount + 0.5;
        double r = Math.sqrt(indices / sample);
        double theta = Math.PI * (1 + Math.sqrt(5)) * indices;
        double x = radius * r * Math.cos(theta);
        double z = radius * r * Math.sin(theta);

        spawnParticle(getOrigin().clone().add(x, 0, z));

        // 进行重置
        if (currentCount > sample) {
            currentCount = 0;
        }
    }

    /**
     * 使用给定的时间播放粒子
     *
     * @param time  持续时间, 单位 tick
     * @param count 粒子数量
     */
    public void playWithTime(long time, long count) {
        if (time == 0) {
            for (int i = 0; i < count; i++) {
                double indices = i + 0.5;
                double r = Math.sqrt(indices / count);
                double theta = Math.PI * (1 + Math.sqrt(5)) * indices;
                double x = radius * r * Math.cos(theta);
                double z = radius * r * Math.sin(theta);

                Location spawnLocation = getOrigin().clone().add(x, 0, z);
                spawnParticle(spawnLocation);
            }
            return;
        }

        new BukkitRunnable() {
            // 这里用来计量当前要播放的粒子是第几个tick, 也可说是帧数
            int frame = 0;
            int sample = 0;

            @Override
            public void run() {
                if (frame >= time) {
                    cancel();
                    return;
                }
                frame++;
                // 每一帧要计算的粒子数量
                int frameTick = (int) (count / time);
                for (double i = sample; i < (frame + 1) * frameTick; i += 1) {
                    double indices = i + 0.5;
                    double r = Math.sqrt(indices / count);
                    double theta = Math.PI * (1 + Math.sqrt(5)) * indices;
                    double x = radius * r * Math.cos(theta);
                    double z = radius * r * Math.sin(theta);


                    Location spawnLocation = getOrigin().clone().add(x, 0, z);
                    spawnParticle(spawnLocation);
                }
                sample += frameTick;
            }
        }.runTaskTimerAsynchronously(ParticleLib.getInstance(), 0L, 1L);
    }

}
