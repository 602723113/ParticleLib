package top.zoyn.particlelib.utils;

import org.bukkit.Bukkit;

public class VersionUtils {

    public static boolean isNewer() {
        String bukkitVersion = Bukkit.getBukkitVersion();
        return !bukkitVersion.contains("1.6") && !bukkitVersion.contains("1.7") && !bukkitVersion.contains("1.8") && !bukkitVersion.contains("1.9") && !bukkitVersion.contains("1.10") && !bukkitVersion.contains("1.11") && !bukkitVersion.contains("1.12");
    }

}
