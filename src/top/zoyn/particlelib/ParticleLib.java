package top.zoyn.particlelib;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 粒子库主类
 *
 * @author Zoyn
 */
public class ParticleLib extends JavaPlugin {

    private static ParticleLib instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getConsoleSender().sendMessage("§e[§6ParticleLib§e]§a粒子库已成功加载");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§e[§6ParticleLib§e]§a粒子库已成功卸载");
    }

    public static ParticleLib getInstance() {
        return instance;
    }

    /**
     * 往控制台上发送一条支持颜色代码的信息
     * @param message 信息
     */
    public static void sendLog(String message) {
        Bukkit.getConsoleSender().sendMessage("§e[§6ParticleLib§e]" + message);
    }
}
