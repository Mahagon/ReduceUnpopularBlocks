package net.mahagon.ReduceUnpopularBlocks;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingPlaceEvent;

public class HangingBlockListener implements Listener
{
	private ReduceUnpopularBlocksPlugin plugin;

	HangingBlockListener(ReduceUnpopularBlocksPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onHangingBlockPlace(HangingPlaceEvent event) {
		Player player = event.getPlayer(); // get player
		Hanging block = event.getEntity(); // get block placed
		// check config
		FileConfiguration config = plugin.getConfig();
		String material = block.getType().toString();
		// if deactivated, don't continue checking
		if(config.getBoolean("searchfor." + material + ".activate")) {
			return;
		}
		int radius = config.getInt("searchfor." + material + ".checkradius");
		int allowednumberofblocks = config.getInt("searchfor." + material + ".allowednumberofblocks");
		int blockCounter = 1;
		List<Entity> entitiesnearby = event.getEntity().getNearbyEntities(radius, radius, radius);
		for(Entity entity : entitiesnearby) {
			if(entity.getType().toString().equalsIgnoreCase(material)) {
				blockCounter++;
			}
		}
		if(blockCounter > allowednumberofblocks) {
			player.sendMessage(ChatColor.RED + "It's not allowed to place more than " + allowednumberofblocks + " blocks of " + material
					+ " in a radius of " + radius + "");
			if(config.getBoolean("general.warnonconsole")) {
				Bukkit.getLogger().info(
						"[" + plugin.getName() + "] " + player.getName() + " tried to set more than " + allowednumberofblocks
								+ " blocks of " + material + " in a radius of " + radius + "");
			}
			event.setCancelled(true);
		}
	}
}
