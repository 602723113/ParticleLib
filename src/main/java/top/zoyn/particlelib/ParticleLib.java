package top.zoyn.particlelib;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.bukkit.util.noise.PerlinNoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import top.zoyn.particlelib.pobject.*;
import top.zoyn.particlelib.pobject.bezier.TwoRankBezierCurve;
import top.zoyn.particlelib.pobject.equation.Equations;
import top.zoyn.particlelib.pobject.equation.GeneralEquationRenderer;
import top.zoyn.particlelib.utils.INoise;
import top.zoyn.particlelib.utils.ImprovedNoise;
import top.zoyn.particlelib.utils.VectorUtils;
import top.zoyn.particlelib.utils.matrix.Matrix;
import top.zoyn.particlelib.utils.matrix.Matrixs;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 粒子库主类
 *
 * @author Zoyn
 */
public class ParticleLib extends JavaPlugin {

    private static ParticleLib instance;

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
        Location origin = player.getLocation();
        Vector direction = origin.getDirection();

//        Arc arc = new Arc(player.getLocation(), 0, 180, 3, 9);
//        arc.calculateLocations()
//                .stream()
//                .map(loc -> {
//                    Vector v = VectorUtils.createVector(loc, arc.getOrigin());
//                    Matrix matrix = Matrixs.rotateAroundYAxis(-player.getLocation().getYaw());
//                    return arc.getOrigin().clone().add(matrix.applyVector(v));
//                }).collect(Collectors.toList());
//
//
//        Random random = new Random();
//        Ray ray = new Ray(origin, direction, 10, 0.33);
//        List<Location> locs = Lists.newArrayList();
//        for (Location location : ray.calculateLocations()) {
//            locs.add(location.clone().add(0, random.nextBoolean() ? 0.5 + random.nextDouble() : 0.5 + (-random.nextDouble()), 0));
//        }
//        for (int i = 0; i < locs.size(); i++) {
//            if (i + 1 == locs.size()) {
//                break;
//            }
//            Line.buildLine(locs.get(i), locs.get(i + 1), 0.02, Particle.DRIP_LAVA);
//        }

//        double radiansI = Math.toRadians(0);
//        double xi = Math.cos(radiansI);
//        double zi = Math.sin(radiansI);
//        Location end = origin.clone().add(xi, 0, zi);
//        Vector v = VectorUtils.createVector(origin, end);
//
//        double xj = 2 * xi;
//        double zj = 2 * zi;
//        Vector v2 = VectorUtils.createVector(end, end.clone().add(xj, 0, zj));
//
//        new BukkitRunnable() {
//
//            @Override
//            public void run() {
//                v.rotateAroundY(10);
//                v2.rotateAroundY(5);
//                end.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, end.clone().add(v).add(v2), 1);
//            }
//        }.runTaskTimerAsynchronously(this, 0, 1);

        return true;
    }
}
