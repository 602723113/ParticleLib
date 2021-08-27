package top.zoyn.particlelib.pobject;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.ParticleLib;
import top.zoyn.particlelib.utils.matrix.Matrix;

/**
 * 表示一个特效对象
 *
 * @author Zoyn IceCold
 */
public abstract class ParticleObject {

    private Location origin;

    private ShowType showType = ShowType.NONE;
    private BukkitTask task;
    private long period;
    private boolean running = false;

    private Particle particle = Particle.VILLAGER_HAPPY;
    private int count = 1;
    private double offsetX = 0;
    private double offsetY = 0;
    private double offsetZ = 0;
    private double extra = 0;
    private Object data = null;
    private Color color;
    private Entity entity;
    private BukkitTask attachTask;
    /**
     * X的变化量
     */
    private double incrementX;
    private double incrementY;
    private double incrementZ;
    /**
     * 表示该特效对象所拥有的矩阵
     */
    private Matrix matrix;

    public static boolean isNewer() {
        String bukkitVersion = Bukkit.getBukkitVersion();
        return !bukkitVersion.contains("1.6") && !bukkitVersion.contains("1.7") && !bukkitVersion.contains("1.8") && !bukkitVersion.contains("1.9") && !bukkitVersion.contains("1.10") && !bukkitVersion.contains("1.11") && !bukkitVersion.contains("1.12");
    }

    public abstract void show();

    public void alwaysShow() {
        turnOffTask();

        // 此处的延迟 2tick 是为了防止turnOffTask还没把特效给关闭时的缓冲
        Bukkit.getScheduler().runTaskLater(ParticleLib.getInstance(), () -> {
            running = true;
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!running) {
                        return;
                    }
                    show();
                }
            }.runTaskTimer(ParticleLib.getInstance(), 0L, period);

            setShowType(ShowType.ALWAYS_SHOW);
        }, 2L);
    }

    public void alwaysShowAsync() {
        turnOffTask();

        // 此处的延迟 2tick 是为了防止turnOffTask还没把特效给关闭时的缓冲
        Bukkit.getScheduler().runTaskLater(ParticleLib.getInstance(), () -> {
            running = true;
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!running) {
                        return;
                    }
                    show();
                }
            }.runTaskTimerAsynchronously(ParticleLib.getInstance(), 0L, period);

            setShowType(ShowType.ALWAYS_SHOW_ASYNC);
        }, 2L);
    }

    public void alwaysPlay() {
        if (!(this instanceof Playable)) {
            try {
                throw new NoSuchMethodException("该对象不支持播放!");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        Playable playable = (Playable) this;
        turnOffTask();

        // 此处的延迟 2tick 是为了防止turnOffTask还没把特效给关闭时的缓冲
        Bukkit.getScheduler().runTaskLater(ParticleLib.getInstance(), () -> {
            running = true;
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!running) {
                        return;
                    }
                    playable.playNextPoint();
                }
            }.runTaskTimer(ParticleLib.getInstance(), 0L, period);

            setShowType(ShowType.ALWAYS_PLAY);
        }, 2L);
    }

    public void alwaysPlayAsync() {
        if (!(this instanceof Playable)) {
            try {
                throw new NoSuchMethodException("该对象不支持播放!");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        Playable playable = (Playable) this;
        turnOffTask();

        // 此处的延迟 2tick 是为了防止turnOffTask还没把特效给关闭时的缓冲
        Bukkit.getScheduler().runTaskLater(ParticleLib.getInstance(), () -> {
            running = true;
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!running) {
                        return;
                    }
                    playable.playNextPoint();
                }
            }.runTaskTimerAsynchronously(ParticleLib.getInstance(), 0L, period);

            setShowType(ShowType.ALWAYS_PLAY_ASYNC);
        }, 2L);
    }

    public void turnOffTask() {
        if (task != null) {
            running = false;
            task.cancel();
            attachTask.cancel();
            setShowType(ShowType.NONE);
        }
    }

    public ParticleObject addMatrix(Matrix matrix) {
        if (this.matrix == null) {
            setMatrix(matrix);
            return this;
        }
        this.matrix = matrix.multiply(this.matrix);
        return this;
    }

    public ParticleObject setMatrix(Matrix matrix) {
        this.matrix = matrix;
        return this;
    }

    public ParticleObject removeMatrix() {
        matrix = null;
        return this;
    }

    public boolean hasMatrix() {
        return matrix != null;
    }

    public Location getOrigin() {
        if (entity != null) {
            return entity.getLocation();
        }
        return origin;
    }

    public ParticleObject setOrigin(Location origin) {
        this.origin = origin;
        return this;
    }

    public long getPeriod() {
        return period;
    }

    public ParticleObject setPeriod(long period) {
        this.period = period;
        return this;
    }

    public ShowType getShowType() {
        return showType;
    }

    public ParticleObject setShowType(ShowType showType) {
        this.showType = showType;
        return this;
    }

    public Particle getParticle() {
        return particle;
    }

    public ParticleObject setParticle(Particle particle) {
        this.particle = particle;
        // 记得重置颜色
        if (color != null) {
            color = null;
        }
        return this;
    }

    public int getCount() {
        return count;
    }

    public ParticleObject setCount(int count) {
        this.count = count;
        return this;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public ParticleObject setOffsetX(double offsetX) {
        this.offsetX = offsetX;
        return this;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public ParticleObject setOffsetY(double offsetY) {
        this.offsetY = offsetY;
        return this;
    }

    public double getOffsetZ() {
        return offsetZ;
    }

    public ParticleObject setOffsetZ(double offsetZ) {
        this.offsetZ = offsetZ;
        return this;
    }

    public double getExtra() {
        return extra;
    }

    public ParticleObject setExtra(double extra) {
        this.extra = extra;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ParticleObject setData(Object data) {
        this.data = data;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public ParticleObject setColor(Color color) {
        this.color = color;
        return this;
    }

    public Entity getEntity() {
        return entity;
    }

    public ParticleObject attachEntity(Entity entity) {
        this.entity = entity;
        return this;
    }

    public ParticleObject setIncrementX(double incrementX) {
        this.incrementX = incrementX;
        return this;
    }

    public ParticleObject setIncrementY(double incrementY) {
        this.incrementY = incrementY;
        return this;
    }

    public ParticleObject setIncrementZ(double incrementZ) {
        this.incrementZ = incrementZ;
        return this;
    }

    /**
     * 通过给定一个坐标就可以使用已经指定的参数来播放粒子
     *
     * @param location 坐标
     */
    public void spawnParticle(Location location) {
        spawnParticle(location, this.particle, count, offsetX, offsetY, offsetZ, extra, data);
    }

    /**
     * 自定义程度较高的生成粒子方法
     *
     * @param location 坐标
     * @param particle 粒子
     * @param count    粒子数量
     * @param offsetX  X轴偏移量
     * @param offsetY  Y轴偏移量
     * @param offsetZ  Z轴偏移量
     * @param extra    粒子额外参数
     * @param data     特殊粒子属性
     */
    public void spawnParticle(Location location, Particle particle, int count, double offsetX, double offsetY, double offsetZ, double extra, Object data) {
        Location showLocation = location;
        if (hasMatrix()) {
            Vector vector = location.clone().subtract(origin).toVector();
            Vector changed = matrix.applyVector(vector);

            showLocation = origin.clone().add(changed);
        }

        // 在这里可以设置一个XYZ的变化量
        showLocation.add(incrementX, incrementY, incrementZ);

        // 可以在这里设置 Color
        if (color != null) {
            if (isNewer()) {
                Particle.DustOptions dust = new Particle.DustOptions(color, 1);
                location.getWorld().spawnParticle(Particle.REDSTONE, showLocation.getX(), showLocation.getY(), showLocation.getZ(), 0, offsetX, offsetY, offsetZ, 1, dust);
            } else {
                // 对低版本的黑色做一个小小的兼容
                if (color.getRed() == 0 && color.getBlue() == 0 && color.getGreen() == 0) {
                    location.getWorld().spawnParticle(Particle.REDSTONE, showLocation.getX(), showLocation.getY(), showLocation.getZ(), 0, Float.MIN_VALUE / 255.0f, Float.MIN_VALUE / 255.0f, Float.MIN_VALUE / 255.0f, 1);
                } else {
                    location.getWorld().spawnParticle(Particle.REDSTONE, showLocation.getX(), showLocation.getY(), showLocation.getZ(), 0, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1);
                }
            }
            return;
        }
        location.getWorld().spawnParticle(particle, showLocation, count, offsetX, offsetY, offsetZ, extra, data);
    }

}