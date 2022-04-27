package CleanChests.event;

import CleanChests.main;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.Inventory;

public class ChunkLoad implements Listener {
    @EventHandler
    public void onLoad(ChunkLoadEvent event) {
        for (BlockState tile : event.getChunk().getTileEntities()) {
            if (tile instanceof Chest chest) {
                Inventory inv = chest.getBlockInventory();
                for (String list : main.instance.getConfig().getStringList("Blocks")) {
                    if (!inv.isEmpty() && inv.contains(Material.matchMaterial(list))) {
                        inv.remove(Material.matchMaterial(list));
                    }
                }
            }
        }
    }
}