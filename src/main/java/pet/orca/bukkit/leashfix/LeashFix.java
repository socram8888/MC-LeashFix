package pet.orca.bukkit.leashfix;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LeashFix extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		// Register event listeners
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = org.bukkit.event.EventPriority.LOWEST)
	public void onEntityTeleport(EntityTeleportEvent event) {
		// Check if event was cancelled by an earlier handler
		if (event.isCancelled()) {
			return;
		}

		Entity entity = event.getEntity();

		// Check if entity teleported to a different world using Bukkit API
		if (!event.getFrom().getWorld().equals(event.getTo().getWorld())) {
			// Get NMS entity and check if it's a Mob, is leashed, and has home
			net.minecraft.world.entity.Entity nmsEntity = ((org.bukkit.craftbukkit.entity.CraftEntity) entity).getHandle();
			if (nmsEntity instanceof net.minecraft.world.entity.Mob mob && mob.isLeashed() && mob.hasHome()) {
				// Clear the home position
				mob.clearHome();
				String message = String.format("[LeashFix] Cleared home for %s after cross-world teleport", entity.getName());
				getLogger().info(message);
				Bukkit.broadcast(message, "bukkit.broadcast.admin");
			}
		}
	}
}
