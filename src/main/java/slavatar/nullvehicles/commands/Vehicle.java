package slavatar.nullvehicles.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import slavatar.nullvehicles.NullVehicles;

import java.util.ArrayList;
import java.util.UUID;

public class Vehicle implements CommandExecutor {

    public static ArrayList<UUID> entitys = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check on valid player & permission
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[NULL] &fOnly for player!"));
            return true;
        }
        if (args.length != 1 || !NullVehicles.vehicles.contains(args[0].toLowerCase())) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NullVehicles.noargs));
            return true;
        }
        if (!sender.hasPermission("nullvehicles." +args[0].toLowerCase())) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NullVehicles.noperms));
            return true;
        }
        Player p = (Player) sender;
        if (!p.isOnGround()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NullVehicles.grounderror));
            return true;
        }

        if (entitys.contains(p.getUniqueId())) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NullVehicles.onlyone));
            return true;
        }

        Entity entity = (Entity) p.getWorld().spawnEntity(p.getLocation(), EntityType.fromName(args[0])); // Spawn entity
        switch (entity.getType().getName().toLowerCase()) { // If it animal
            case "horse":
                Horse horse = (Horse) entity;
                horse.setTamed(true);
                horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
                break;
            case "donkey":
                Donkey donkey = (Donkey) entity;
                donkey.setTamed(true);
                donkey.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
                break;
            case "llama":
                Llama llama = (Llama) entity;
                llama.setTamed(true);
                llama.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
                break;
        }
        entity.setPassenger(p); // Ride on entity
        entity.setCustomName(p.getDisplayName());
        entitys.add(entity.getUniqueId());
        entitys.add(p.getUniqueId());

        // Send message & sound
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', NullVehicles.spawned.replace("{0}", args[0])));
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1f);

        return true;
    }
}
