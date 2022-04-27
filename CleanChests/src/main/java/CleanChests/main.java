package CleanChests;

import CleanChests.cmd.CleanChests;
import CleanChests.event.ChunkLoad;
import CleanChests.event.Completer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
    public static main instance;

    public main() {
        instance = this;
    }

    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("CleanChests >> enabled");
        getCommand("cleanchests").setExecutor(new CleanChests());
        getCommand("cleanchests").setTabCompleter(new Completer());
        Bukkit.getPluginManager().registerEvents(new ChunkLoad(), this);
    }

    public void onDisable() {
        getLogger().info("CleanChests >> disabled");
    }

    public String convertString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string.replace("[prefix]", getConfig().getString("CleanChest.prefix")));
    }
}