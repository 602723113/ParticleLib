package top.zoyn.particlelib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

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

        return true;
    }
}
