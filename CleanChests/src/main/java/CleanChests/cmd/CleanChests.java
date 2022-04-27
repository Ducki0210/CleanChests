package CleanChests.cmd;

import CleanChests.main;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CleanChests implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(main.instance.convertString((main.instance.getConfig().getString("CleanChest.messages.console"))));
        } else {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    main.instance.reloadConfig();
                    player.sendMessage(main.instance.convertString(main.instance.getConfig().getString("CleanChest.messages.reload")));
                    player.playSound(player.getLocation(), Sound.valueOf(main.instance.getConfig().getString("CleanChest.messages.sound.reload")), 1, 1);
                } else if (args[0].equalsIgnoreCase("addItem")) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    List<String> blocks = main.instance.getConfig().getStringList("Blocks");
                    if (item.getType() != Material.AIR) {
                        if (!blocks.contains(item.getType().toString())) {
                            player.sendMessage(main.instance.convertString(main.instance.getConfig().getString("CleanChest.messages.itemAdded")));
                            player.playSound(player.getLocation(), Sound.valueOf(main.instance.getConfig().getString("CleanChest.messages.sound.allow")), 1, 1);
                            blocks.add(item.getType().toString());
                            main.instance.getConfig().set("Blocks", blocks);
                            main.instance.saveConfig();
                        } else {
                            player.sendMessage(main.instance.convertString(main.instance.getConfig().getString("CleanChest.messages.alreadyContains")));
                            player.playSound(player.getLocation(), Sound.valueOf(main.instance.getConfig().getString("CleanChest.messages.sound.deny")), 1, 1);
                        }
                    } else {
                        player.sendMessage(main.instance.convertString(main.instance.getConfig().getString("CleanChest.messages.itemHand")));
                        player.playSound(player.getLocation(), Sound.valueOf(main.instance.getConfig().getString("CleanChest.messages.sound.deny")), 1, 1);
                    }
                } else {
                    player.sendMessage(main.instance.convertString(main.instance.getConfig().getString("CleanChest.messages.usage").replace("[cmd]", cmd.getName())));
                    player.playSound(player.getLocation(), Sound.valueOf(main.instance.getConfig().getString("CleanChest.messages.sound.deny")), 1, 1);
                }
            } else {
                int chests = 0;

                for (Chunk chunks : player.getWorld().getLoadedChunks()) {
                    for (BlockState tile : chunks.getTileEntities()) {
                        if (tile instanceof Chest chest) {
                            Inventory inv = chest.getBlockInventory();
                            for (String list : main.instance.getConfig().getStringList("Blocks")) {
                                if (!inv.isEmpty() && inv.contains(Material.matchMaterial(list))) {
                                    inv.remove(Material.matchMaterial(list));
                                    chests += 1;
                                }
                            }
                        }
                    }
                }

                if (main.instance.getConfig().getBoolean("enderchest")) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        for (String list : main.instance.getConfig().getStringList("Blocks")) {
                            if (!online.getEnderChest().isEmpty() && online.getEnderChest().contains(Material.matchMaterial(list))) {
                                online.getEnderChest().remove(Material.matchMaterial(list));
                                chests += 1;
                            }
                        }
                    }
                }

                player.sendMessage(main.instance.convertString(main.instance.getConfig().getString("CleanChest.messages.cleanChests").replace("[value]", String.valueOf(chests))));
                player.playSound(player.getLocation(), Sound.valueOf(main.instance.getConfig().getString("CleanChest.messages.sound.allow")), 1, 1);
            }
        }
        return false;
    }
}