package top.zoyn.particlelib;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import top.zoyn.particlelib.pobject.*;
import top.zoyn.particlelib.utils.matrix.Matrixs;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 粒子库主类
 *
 * @author Zoyn
 */
public class ParticleLib extends JavaPlugin {

    private static ParticleLib instance;
    private final AtomicInteger angle = new AtomicInteger(0);
    private Location loc1;
    private Location loc2;
    private Circle circle;

    public static ParticleLib getInstance() {
        return instance;
    }

    /**
     * 往控制台上发送一条支持颜色代码的信息
     *
     * @param message 信息
     */
    public static void sendLog(String message) {
        Bukkit.getConsoleSender().sendMessage("§e[§6ParticleLib§e] " + message);
    }

    /**
     * 围绕一个方块画出其边框
     *
     * @param block    给定的方块
     * @param particle 要显示的粒子
     */
    public static void showBorderAboutBlock(Block block, Particle particle) {
        Location low = block.getLocation();
        Location high = low.clone().add(0, 1, 0);
        List<Location> lowers = Lists.newArrayList(
                low,
                low.clone().add(1, 0, 0),
                low.clone().add(1, 0, 1),
                low.clone().add(0, 0, 1));
        List<Location> highers = Lists.newArrayList(
                high,
                high.clone().add(1, 0, 0),
                high.clone().add(1, 0, 1),
                high.clone().add(0, 0, 1));
        for (int i = 0; i < lowers.size(); i++) {
            Location origin = lowers.get(i);
            Location top = highers.get(i);
            Location next;
            Location topNext;
            // 最后一个的时候
            if (i == 3) {
                next = lowers.get(0);
                topNext = highers.get(0);
            } else {
                next = lowers.get(i + 1);
                topNext = highers.get(i + 1);
            }

            // 以下为画线操作
            Vector vectorON = next.clone().subtract(origin).toVector().normalize();
            Vector vectorOT = top.clone().subtract(origin).toVector().normalize();
            Vector vectorTT = topNext.clone().subtract(top).toVector().normalize();
            for (double j = 0; j < 1; j += 0.1) {
                low.getWorld().spawnParticle(particle, origin.clone().add(vectorON.clone().multiply(j)), 1, 0, 0, 0, 0);
                low.getWorld().spawnParticle(particle, origin.clone().add(vectorOT.clone().multiply(j)), 1, 0, 0, 0, 0);
                low.getWorld().spawnParticle(particle, top.clone().add(vectorTT.clone().multiply(j)), 1, 0, 0, 0, 0);
            }
        }
    }

    /**
     * 围绕一个方块画出其边框
     *
     * @param block    给定的方块
     * @param particle 要显示的粒子
     */
    public static void showBorderAndGridAboutBlock(Block block, Particle particle) {
        Location low = block.getLocation();
        Location high = low.clone().add(0, 5, 0);
        List<Location> lowers = Lists.newArrayList(
                low,
                low.clone().add(5, 0, 0),
                low.clone().add(5, 0, 5),
                low.clone().add(0, 0, 5));
        List<Location> highers = Lists.newArrayList(
                high,
                high.clone().add(5, 0, 0),
                high.clone().add(5, 0, 5),
                high.clone().add(0, 0, 5));

        Grid grid = new Grid(low, lowers.get(2), 1.4D);
        grid.setParticle(Particle.FIREWORKS_SPARK);
        grid.show();
        Grid grid2 = new Grid(high, highers.get(2), 1.4D);
        grid2.setParticle(Particle.FIREWORKS_SPARK);
        grid2.show();
        for (int i = 0; i < lowers.size(); i++) {
            Location origin = lowers.get(i);
            Location top = highers.get(i);
            Location next;
            Location topNext;
            // 最后一个的时候
            if (i == 3) {
                next = lowers.get(0);
                topNext = highers.get(0);
            } else {
                next = lowers.get(i + 1);
                topNext = highers.get(i + 1);
            }

            // 以下为画线操作
            Vector vectorON = next.clone().subtract(origin).toVector().normalize();
            Vector vectorOT = top.clone().subtract(origin).toVector().normalize();
            Vector vectorTT = topNext.clone().subtract(top).toVector().normalize();
            for (double j = 0; j < 5; j += 0.1) {
                low.getWorld().spawnParticle(particle, origin.clone().add(vectorON.clone().multiply(j)), 1, 0, 0, 0, 0);
                low.getWorld().spawnParticle(particle, origin.clone().add(vectorOT.clone().multiply(j)), 1, 0, 0, 0, 0);
                low.getWorld().spawnParticle(particle, top.clone().add(vectorTT.clone().multiply(j)), 1, 0, 0, 0, 0);
            }
            // 绘制网格面
            Grid grid3 = new Grid(origin, topNext, 1.4D);
            grid3.setParticle(Particle.FIREWORKS_SPARK);
            grid3.show();
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        sendLog("§a粒子库已成功加载");
    }

    @Override
    public void onDisable() {
        sendLog("§a粒子库已成功卸载");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

//        Location location = player.getLocation().clone();
//        Bukkit.getScheduler().runTaskTimer(this, () -> showBorderAndGridAboutBlock(location.getBlock(), Particle.FIREWORKS_SPARK), 0L, 10L);


//        Circle circle = new Circle(player.getLocation());
//        circle.setStep(10D);
//
//        Astroid astroid = new Astroid(player.getLocation());
//        astroid.setParticle(Particle.FIREWORKS_SPARK);
//        astroid.setStep(10D);
//        astroid.alwaysPlayAsync();
//
//        Heart heart = new Heart(player.getLocation());
//        heart.setXScaleRate(1.5D);
//        heart.setYScaleRate(1.5D);
//        heart.setParticle(Particle.FLAME);
//        heart.setStep(0.05D);
//
//        Heart heart2 = new Heart(player.getLocation());
//        heart2.setXScaleRate(1.5D);
//        heart2.setYScaleRate(1.5D);
//        heart2.addMatrix(Matrixs.rotate2D(90));
//        heart2.setParticle(Particle.FLAME);
//        heart2.setStep(0.05D);
//
//        Heart heart3 = new Heart(player.getLocation());
//        heart3.setXScaleRate(1.5D);
//        heart3.setYScaleRate(1.5D);
//        heart3.addMatrix(Matrixs.rotate2D(180));
//        heart3.setParticle(Particle.FLAME);
//        heart3.setStep(0.05D);
//
//        Heart heart4 = new Heart(player.getLocation());
//        heart4.setXScaleRate(1.5D);
//        heart4.setYScaleRate(1.5D);
//        heart4.addMatrix(Matrixs.rotate2D(270));
//        heart4.setParticle(Particle.FLAME);
//        heart4.setStep(0.05D);
//
//        EffectGroup group = new EffectGroup()
//                .addEffect(circle)
//                .addEffect(astroid)
//                .addEffect(heart)
//                .addEffect(heart2)
//                .addEffect(heart3)
//                .addEffect(heart4)
//                .scale(2)
//                .rotate(45)
//                .setPeriod(1)
//                .alwaysPlayAsync();

//        Arc arc = new Arc(player.getLocation());
//        arc.setAngle(360D);
//        arc.setStep(15D);
//        arc.setPeriod(2L);
//        arc.alwaysPlayAsync();

//        Grid grid = new Grid(player.getLocation(), player.getLocation().add(5, 0, 5), 1.2D);
//
//        grid.setPeriod(20);
//        grid.alwaysShowAsync();

//        Vector vector = player.getLocation().getDirection();
//        Location location = player.getLocation();
//        World world = location.getWorld();
//        ThreeDProjector projector = n ew ThreeDProjector(location, vector);
//        Bukkit.getScheduler().runTaskTimer(this, () -> {
//            double y = 0;
//            for (int i = 0; i < 8 * 360; i += 20) {
//                double rad = Math.toRadians(i);
//                double x = Math.cos(rad);
//                y += 0.1;
//                double z = Math.sin(rad);
//                // 通过投影器开始转换坐标
//                Location loc = projector.apply(x, y, z);
//                world.spawnParticle(Particle.VILLAGER_HAPPY, loc, 1, 0, 0, 0, 0);
//            }
//        }, 0L, 10L);

//        Vector vector = player.getLocation().getDirection();
//        Location location = player.getLocation();
//        World world = location.getWorld();
//        BiFunction<Double, Double, Location> method = TwoDProjector.create2DProjector(location, vector);
//        Bukkit.getScheduler().runTaskTimer(this, () -> {
//            for (int i = 0; i < 360; i++) {
//                double rad = Math.toRadians(i);
//                double x = Math.cos(rad);
//                double z = Math.sin(rad);
//                // 通过投影器开始转换坐标
//                Location loc = method.apply(x, z);
//                world.spawnParticle(Particle.VILLAGER_HAPPY, loc, 1, 0, 0, 0, 0);
//            }
//        }, 0L, 10L);

//        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
//            showBorderAboutBlock(player.getLocation().getBlock(), Particle.FIREWORKS_SPARK);
//        }, 0, 7L);

//        Line.buildLine(flipHigh, high.clone().add(-1, 0, 0), 0.1, Particle.VILLAGER_HAPPY);
//        Line.buildLine(flipHigh, high.clone().add(0, 0, -1), 0.1, Particle.VILLAGER_HAPPY);
//
//        Line.buildLine(flipLow, low.clone().add(1, 0, 0), 0.1, Particle.VILLAGER_HAPPY);
//        Line.buildLine(flipLow, low.clone().add(0, 0, 1), 0.1, Particle.VILLAGER_HAPPY);
//
//        Line.buildLine(low, flipHigh, 0.1, Particle.VILLAGER_HAPPY);
//        Line.buildLine(high, flipLow, 0.1, Particle.VILLAGER_HAPPY);
//        double width = 1;
//        double length = 1;
//        double height = 1;

//        Vector vector = new Vector(0, 0, 0);


//        ThreeDProjector projector = new ThreeDProjector(player.getLocation(), player.getLocation().getDirection());
//        double y = 0D;
//        for (int i = 0; i < 360 * 8; i += 5) {
//            double radians = Math.toRadians(i);
//            double x = Math.cos(radians);
//            y += 0.02;
//            double z = Math.sin(radians);
//
//            player.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, projector.apply(x, y, z), 1);
//        }

//        Location launchLocation = player.getEyeLocation().add(player.getLocation().getDirection().multiply(1.2));
//        Ray ray = new Ray(launchLocation, player.getLocation().getDirection(), 10, 0.02);
//
//        ray.setStopType(Ray.RayStopType.HIT_ENTITY)
//                .setHitEntityConsumer(entity -> {
//                    entity.setCustomName("§a芜湖!");
//                    entity.setCustomNameVisible(true);
//                })
//                .setEntityFilter(entity -> entity.getName().equalsIgnoreCase(player.getName()));
//
//        ray.show();
//
//        Ray ray = new Ray(launchLocation, player.getLocation().getDirection(), 10, 0.02, 0.5D, Ray.RayStopType.HIT_ENTITY, entity -> {
//            entity.setCustomName("§a芜湖!");
//            entity.setCustomNameVisible(true);
//        }, entity -> entity.getName().equalsIgnoreCase(player.getName()));

//
//        Astroid astroid = new Astroid(player.getLocation());
//        astroid.setParticle(Particle.FIREWORKS_SPARK);
//        astroid.show();


//
//        Heart heart = new Heart(player.getLocation());
//        heart.alwaysShowAsync();

//        Polygon polygon = new Polygon(4, player.getLocation());
//        polygon.setParticle(Particle.FLAME);
//        polygon.setStep(0.5);
//        polygon.alwaysShowAsync();


//
//        polygon = new Polygon(3, player.getLocation());
//        polygon.setMatrix(Matrixs.rotate2D(90).multiply(2));
//        polygon.setParticle(Particle.FLAME);
//        polygon.setStep(0.5);
//        polygon.alwaysShowAsync();

//        Arc arc = new Arc(player.getLocation(), 90D);
//        arc.setMatrix(Matrixs.eyes(2, 2).multiply(2));
//        arc.setStep(10);
//        arc.alwaysShowAsync();

//        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
//            if (angle.get() == 360) {
//                angle.set(0);
//            }
//
//            Matrix2D m1 = Matrixs.rotate2D(angle.getAndAdd(10));
//            polygon.setMatrix(m1);
//            polygon.resetLocations();
//        }, 0, 7L);
//        polygon.alwaysShowAsync();

//        Location location = player.getLocation();
//        Vector axis = player.getEyeLocation().getDirection();
//        for (double angle = 0; angle <= 360; angle += 45) {
//            Location newLoc = LocationUtils.rotateLocationAboutVector(location, player.getEyeLocation(), angle, axis);
//            newLoc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, newLoc, 1);
//        }

//        Location p0 = player.getLocation();
//        Location p1 = player.getLocation().add(0,3,0);
//        Location p2 = player.getLocation().add(3,0,0);
//        Location p3 = p2.clone().add(2, 5,0);
//        ThreeRankBezierCurve threeRank = new ThreeRankBezierCurve(p0, p1, p2, p3);
//        threeRank.alwaysShowAsync();


//        Sphere sphere = new Sphere(player.getLocation());
//        sphere.setSample(100)
//                .setRadius(2);
//        sphere.alwaysShowAsync();


//        Circle circle = new Circle(player.getLocation());
//        circle.alwaysShowAsync();

//        Arc arc = new Arc(player.getLocation())
//                .setAngle(180D) // 设置弧的角度
//                .setRadius(1.5) // 设置弧距离原点的距离
//                .setStep(2); // 设置弧上的每个粒子点的间隔
//        arc.alwaysShowAsync();
//        Location loc1 = player.getLocation();
//        Location loc2 = player.getEyeLocation();
//        Line line = new Line(loc1, loc2);
//        line.alwaysShowAsync();

//        circle = new Circle(player.getLocation());
//        circle.setRadius(5D)
//                .setOrigin(player.getLocation().add(1, 0, 0));
//        circle.alwaysShowAsync();

//        PlayerBackCoordinate coordinate = new PlayerBackCoordinate(player.getLocation());
//        Location loc = coordinate.newLocation(-4, 2.5, 0);
//        Location p1 = coordinate.newLocation(0, 0, 0);
//        Location p2 = coordinate.newLocation(4, 4, 0);
//        Location p3 = coordinate.newLocation(8, -2, -2);
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                loc.getWorld().spawnParticle(Particle.FLAME, loc, 5, 0, 0, 0, 0);
//                loc.getWorld().spawnParticle(Particle.FLAME, p1, 5, 0, 0, 0, 0);
//                loc.getWorld().spawnParticle(Particle.FLAME, p2, 5, 0, 0, 0, 0);
//                loc.getWorld().spawnParticle(Particle.FLAME, p3, 5, 0, 0, 0, 0);
//            }
//        }.runTaskTimerAsynchronously(this, 0L, 10L);

        // 抛物线
//        GeneralEquationRenderer renderer = new GeneralEquationRenderer(player.getLocation(), x -> Math.pow(x, 2));
//        renderer.alwaysShowAsync();

//        ParametricEquationRenderer renderer = new ParametricEquationRenderer(player.getLocation(), t -> 2 * Math.pow(Math.cos(t), 3), t -> 2 * Math.pow(Math.sin(t), 3));
//        renderer.alwaysShowAsync();

//        ParametricEquationRenderer renderer = new ParametricEquationRenderer(player.getLocation(), Math::cos, Math::sin);
//        renderer.setDt(30);
//        renderer.alwaysShowAsync();
//
//        PolarEquationRenderer renderer1 = new PolarEquationRenderer(player.getLocation(), theta -> {
//            return 1.5 * Math.sin(2 * theta);
//        });
//        renderer1.alwaysShowAsync();

//        PlayerFixedCoordinate coordinate = new PlayerFixedCoordinate(player.getLocation());
//
//        Location location = coordinate.newLocation(1, 0, 1);
//        World world = location.getWorld();
//        world.spawnParticle(Particle.FLAME, location, 1, 0, 0, 0, 0);
//
//        location = coordinate.newLocation(1,1,1);
//        world.spawnParticle(Particle.VILLAGER_HAPPY, location, 1, 0, 0, 0, 0);
//        player.sendMessage("Already Done.");
//
//        PlayerFrontCoordinate coordinate = new PlayerFrontCoordinate(player.getLocation());
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                Location location = player.getLocation();
//                World world = location.getWorld();
//                double height = 0;
//                for (int i = 0; i < 360 * 4; i++) {
//                    double rad = Math.toRadians(i);
//                    double x = Math.cos(rad);
//                    double z = Math.sin(rad);
//
//                    location = coordinate.newLocation(x, height, z);
////                    location.add(x, height, z);
//                    world.spawnParticle(Particle.FLAME, location, 1, 0, 0, 0, 0);
//                    height += 0.002;
//                }
//            }
//        }.runTaskTimer(this, 0L, 10L);
//
//
        return true;
    }
}
