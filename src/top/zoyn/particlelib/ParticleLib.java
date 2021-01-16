package top.zoyn.particlelib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import top.zoyn.particlelib.pobject.Arc;
import top.zoyn.particlelib.pobject.Astroid;
import top.zoyn.particlelib.pobject.Circle;
import top.zoyn.particlelib.pobject.Heart;
import top.zoyn.particlelib.utils.matrix.Matrixs;

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
//
//        Astroid astroid = new Astroid(player.getLocation());
//        astroid.setParticle(Particle.FIREWORKS_SPARK);
//        astroid.show();
//
//        Heart heart = new Heart(player.getLocation());
//        heart.alwaysShowAsync();

//        Polygon polygon = new Polygon(3, player.getLocation());
//        polygon.setParticle(Particle.VILLAGER_HAPPY);
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
//        Vector vector = player.getLocation().getDirection();
//        Location location = player.getLocation();
//        World world = location.getWorld();
//        BiFunction<Double, Double, Location> method = TwoDProjector.create2DProjector(location, vector);
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 360; i++) {
//                    double rad = Math.toRadians(i);
//                    double x = Math.cos(rad);
//                    double z = Math.sin(rad);
//
////                    Location loc = method.apply(x, z);
//                    Location loc = location.clone().add(1, 0, 1);
//                    world.spawnParticle(Particle.VILLAGER_HAPPY, loc, 1, 0, 0, 0, 0);
////                    world.spawnParticle(Particle.FLAME, loc, 1, 0, 0, 0, 0);
//                    world.spawnParticle(Particle.FLAME, method.apply(1D, 1D), 1, 0, 0, 0, 0);
//                }
//            }
//        }.runTaskTimer(this, 0L, 10L);


        return true;
    }
}
