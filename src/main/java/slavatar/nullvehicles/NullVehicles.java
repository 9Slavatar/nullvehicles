package slavatar.nullvehicles;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.event.entity.EntityDismountEvent;
import slavatar.nullvehicles.commands.Vehicle;
import slavatar.nullvehicles.commands.Vehicles;

import java.util.List;
import java.util.UUID;

public final class NullVehicles extends JavaPlugin implements Listener {
    public static List<String> vehicles;
    public static String noperms;
    public static String noargs;
    public static String spawned;
    public static String vehiclelist;
    public static List<String> tamedanimals;
    public static String grounderror;
    public static String onlyone;

    @Override
    public void onEnable() {
        // Register all commands
        getCommand("vehicles").setExecutor(new Vehicles());
        getCommand("vehicle").setExecutor(new Vehicle());

        // Work with config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Register events
        getServer().getPluginManager().registerEvents(this, this);

        // Make variables for easy&fast work
        vehicles = getConfig().getStringList("Vehicles");
        noperms = getConfig().getString("NoPermission");
        noargs = getConfig().getString("NoArgs");
        spawned = getConfig().getString("Spawned");
        vehiclelist = getConfig().getString("VehicleList").replace("{0}", vehicles.toString());
        tamedanimals = getConfig().getStringList("TamedAnimals");
        grounderror = getConfig().getString("GroundError");
        onlyone = getConfig().getString("OnlyOne");
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (Vehicle.entitys.contains(event.getDismounted().getUniqueId())) {
            event.getDismounted().remove();
            Vehicle.entitys.remove(event.getDismounted().getUniqueId());
            Vehicle.entitys.remove(event.getEntity().getUniqueId());

            Player p = (Player) event.getEntity();
            p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 0.5f, 1f);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (Vehicle.entitys.contains(event.getPlayer().getUniqueId())) {
            UUID entity = Vehicle.entitys.get(Vehicle.entitys.indexOf(event.getPlayer().getUniqueId()) - 1);
            getServer().getEntity(entity).remove();

            Vehicle.entitys.remove(entity);
            Vehicle.entitys.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (Vehicle.entitys.contains(event.getEntity().getUniqueId())) {
            UUID entity = Vehicle.entitys.get(Vehicle.entitys.indexOf(event.getEntity().getUniqueId()) - 1);
            getServer().getEntity(entity).remove();

            Vehicle.entitys.remove(entity);
            Vehicle.entitys.remove(event.getEntity().getUniqueId());
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) return;
        if (Vehicle.entitys.contains(event.getEntity().getUniqueId())) {
            Vehicle.entitys.remove(event.getEntity().getUniqueId());
            Vehicle.entitys.remove(getServer().getPlayer(event.getEntity().getCustomName()).getUniqueId());

            event.getDrops().clear();
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!Vehicle.entitys.contains(event.getPlayer().getUniqueId())) return;
        if (event.getInventory() instanceof HorseInventory) event.setCancelled(true);
    }
}
