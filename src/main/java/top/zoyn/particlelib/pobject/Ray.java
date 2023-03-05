package top.zoyn.particlelib.pobject;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.ParticleLib;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 代表一个射线
 *
 * @author Zoyn IceCold
 */
public class Ray extends ParticleObject implements Playable {

    private Vector direction;
    private double maxLength;
    private double step;
    /**
     * 用于检测实体时获取周围实体的范围
     */
    private double range;
    private RayStopType stopType;
    private Consumer<Entity> hitEntityConsumer;
    private Predicate<Entity> entityFilter;

    private double currentStep = 0D;

    public Ray(Location origin, Vector direction, double maxLength) {
        this(origin, direction, maxLength, 0.2D);
    }

    public Ray(Location origin, Vector direction, double maxLength, double step) {
        this(origin, direction, maxLength, step, 0.5D, RayStopType.MAX_LENGTH, null);
    }

    public Ray(Location origin, Vector direction, double maxLength, double step, double range, RayStopType stopType, Consumer<Entity> hitEntityConsumer) {
        this(origin, direction, maxLength, step, range, stopType, hitEntityConsumer, null);
    }

    public Ray(Location origin, Vector direction, double maxLength, double step, double range, RayStopType stopType, Consumer<Entity> hitEntityConsumer, Predicate<Entity> entityFilter) {
        setOrigin(origin);
        this.direction = direction;
        this.maxLength = maxLength;
        this.step = step;
        this.range = range;
        this.stopType = stopType;
        this.hitEntityConsumer = hitEntityConsumer;
        this.entityFilter = entityFilter;
    }

    @Override
    public List<Location> calculateLocations() {
        List<Location> points = Lists.newArrayList();

        for (double i = 0; i < maxLength; i += step) {
            Vector vectorTemp = direction.clone().multiply(i);
            Location spawnLocation = getOrigin().clone().add(vectorTemp);

            points.add(spawnLocation);

            if (stopType.equals(RayStopType.HIT_ENTITY)) {
                Collection<Entity> nearbyEntities = spawnLocation.getWorld().getNearbyEntities(spawnLocation, range, range, range);
                List<Entity> entities = Lists.newArrayList();
                // 检测有无过滤器
                if (entityFilter != null) {
                    for (Entity entity : nearbyEntities) {
                        if (!entityFilter.test(entity)) {
                            entities.add(entity);
                        }
                    }
                } else {
                    entities = (List<Entity>) nearbyEntities;
                }

                // 获取首个实体
                if (entities.size() != 0) {
                    break;
                }
            }
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
        for (double i = 0; i < maxLength; i += step) {
            Vector vectorTemp = direction.clone().multiply(i);
            Location spawnLocation = getOrigin().clone().add(vectorTemp);

            spawnParticle(spawnLocation);

            if (stopType.equals(RayStopType.HIT_ENTITY)) {
                Collection<Entity> nearbyEntities = spawnLocation.getWorld().getNearbyEntities(spawnLocation, range, range, range);
                List<Entity> entities = Lists.newArrayList();
                // 检测有无过滤器
                if (entityFilter != null) {
                    for (Entity entity : nearbyEntities) {
                        if (!entityFilter.test(entity)) {
                            entities.add(entity);
                        }
                    }
                } else {
                    entities = (List<Entity>) nearbyEntities;
                }

                // 获取首个实体
                if (entities.size() != 0) {
                    hitEntityConsumer.accept(entities.get(0));
                    break;
                }
            }
        }
    }

    @Override
    public void play() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // 进行关闭
                if (currentStep > maxLength) {
                    cancel();
                    return;
                }
                currentStep += step;
                Vector vectorTemp = direction.clone().multiply(currentStep);
                Location spawnLocation = getOrigin().clone().add(vectorTemp);

                spawnParticle(spawnLocation);

                if (stopType.equals(RayStopType.HIT_ENTITY)) {
                    Collection<Entity> nearbyEntities = spawnLocation.getWorld().getNearbyEntities(spawnLocation, range, range, range);
                    List<Entity> entities = Lists.newArrayList();
                    // 检测有无过滤器
                    if (entityFilter != null) {
                        for (Entity entity : nearbyEntities) {
                            if (!entityFilter.test(entity)) {
                                entities.add(entity);
                            }
                        }
                    } else {
                        entities = (List<Entity>) nearbyEntities;
                    }

                    // 获取首个实体
                    if (entities.size() != 0) {
                        hitEntityConsumer.accept(entities.get(0));
                        cancel();
                    }
                }
            }
        }.runTaskTimer(ParticleLib.getInstance(), 0, getPeriod());
    }

    @Override
    public void playNextPoint() {
        currentStep += step;
        Vector vectorTemp = direction.clone().multiply(currentStep);
        Location spawnLocation = getOrigin().clone().add(vectorTemp);

        spawnParticle(spawnLocation);

        if (stopType.equals(RayStopType.HIT_ENTITY)) {
            Collection<Entity> nearbyEntities = spawnLocation.getWorld().getNearbyEntities(spawnLocation, range, range, range);
            List<Entity> entities = Lists.newArrayList();
            // 检测有无过滤器
            if (entityFilter != null) {
                for (Entity entity : nearbyEntities) {
                    if (!entityFilter.test(entity)) {
                        entities.add(entity);
                    }
                }
            } else {
                entities = (List<Entity>) nearbyEntities;
            }

            // 获取首个实体
            if (entities.size() != 0) {
                hitEntityConsumer.accept(entities.get(0));
                return;
            }
        }

        if (currentStep > maxLength) {
            currentStep = 0D;
        }
    }

    public Vector getDirection() {
        return direction;
    }

    public Ray setDirection(Vector direction) {
        this.direction = direction;
        return this;
    }

    public double getMaxLength() {
        return maxLength;
    }

    public Ray setMaxLength(double maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public double getStep() {
        return step;
    }

    public Ray setStep(double step) {
        this.step = step;
        return this;
    }

    public double getRange() {
        return range;
    }

    public Ray setRange(double range) {
        this.range = range;
        return this;
    }

    public RayStopType getStopType() {
        return stopType;
    }

    public Ray setStopType(RayStopType stopType) {
        this.stopType = stopType;
        return this;
    }

    public Consumer<Entity> getHitEntityConsumer() {
        return hitEntityConsumer;
    }

    public Ray setHitEntityConsumer(Consumer<Entity> hitEntityConsumer) {
        this.hitEntityConsumer = hitEntityConsumer;
        return this;
    }

    public Predicate<Entity> getEntityFilter() {
        return entityFilter;
    }

    public Ray setEntityFilter(Predicate<Entity> entityFilter) {
        this.entityFilter = entityFilter;
        return this;
    }

    public enum RayStopType {
        /**
         * 固定长度(同时也是最大长度)
         */
        MAX_LENGTH,
        /**
         * 碰撞至实体时停止
         */
        HIT_ENTITY,
    }

}
