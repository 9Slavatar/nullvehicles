package slavatar.nullvehicles.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import slavatar.nullvehicles.NullVehicles;

public class Vehicles implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check on valid player & permission
        if (!sender.hasPermission("nullvehicles.vehicles")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NullVehicles.noperms));
            return true;
        }

        // Get vehicles list from cfg & send message
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',  NullVehicles.vehiclelist));
        return true;
    }
}
