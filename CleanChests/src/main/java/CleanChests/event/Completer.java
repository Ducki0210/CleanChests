package CleanChests.event;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class Completer implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1 && sender.hasPermission("Cleanchests.use")) {
            List<String> result = new ArrayList<>();
            result.add("reload");
            result.add("addItem");
            return result;
        }
        return null;
    }
}