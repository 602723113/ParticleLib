package top.zoyn.particlelib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import top.zoyn.particlelib.pobject.Circle;
import top.zoyn.particlelib.pobject.PolarEquationRenderer;

/**
 * 粒子库主类
 *
 * @author Zoyn
 */
public class ParticleLib extends JavaPlugin {

    private static ParticleLib instance;
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
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("loc1")) {
                loc1 = player.getLocation();
                return true;
            }
            if (args[0].equalsIgnoreCase("loc2")) {
                loc2 = player.getLocation();
                return true;
            }

            if (args[0].equalsIgnoreCase("circle")) {
                circle.setOrigin(player.getLocation());
                return true;
            }

            if (args[0].equalsIgnoreCase("cancel")) {
                circle.turnOffTask();
                return true;
            }
        }

//        circle = new Circle(player.getLocation());
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

//        Sphere sphere = new Sphere(player.getLocation());
//        sphere.alwaysShowAsync();
//
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                sphere.setRadius(5)
//                        .setOrigin(player.getLocation())
//                        .setSample(100);
//            }
//        }.runTaskLater(this, 5 * 20L);


        // 抛物线
//        FunctionRenderer renderer = new FunctionRenderer(player.getLocation(), Functions.QUADRATIC_FUNCTION);
//        renderer.alwaysShowAsync();

//        GeneralFunctionRenderer renderer = new GeneralFunctionRenderer(player.getLocation(), x -> {
//            return (8 * Math.pow(1, 3)) / (Math.pow(x, 2) + (4 * Math.pow(1, 2)));
//        });
//        renderer.alwaysShowAsync();

//        ParametricFunctionRenderer renderer = new ParametricFunctionRenderer(player.getLocation(), t -> 2 * Math.pow(Math.cos(t), 3), t -> 2 * Math.pow(Math.sin(t), 3));
//        renderer.alwaysShowAsync();

//        ParametricFunctionRenderer renderer = new ParametricFunctionRenderer(player.getLocation(), Math::cos, Math::sin);
//        renderer.setDt(30);
//        renderer.alwaysShowAsync();

        PolarEquationRenderer renderer1 = new PolarEquationRenderer(player.getLocation(), theta -> {
            return 1.5 * Math.sin(2 * theta);
        });
        renderer1.alwaysShowAsync();

//        Line line = new Line(loc1, loc2);
//        line.show();

//        Arc arc = new Arc(player.getLocation())
//                .setAngle(180D)
//                .setRadius(1.5)
//                .setStep(2);
////        Arc arc = new Arc(player.getLocation(), 180D, 1.5, 2);
//        arc.show();
//        line.alwaysShowAsync();

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
