package net.mahagon.ReduceUnpopularBlocks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener
{

	private ReduceUnpopularBlocksPlugin plugin;

	public BlockListener(ReduceUnpopularBlocksPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer(); // get player
		Block block = event.getBlockPlaced(); // get block placed
		// check config
		FileConfiguration config = plugin.getConfig();
		String material = block.getType().toString();
		// if deactivated, don't continue checking
		if(config.getBoolean("searchfor." + material + ".activate") == false) {
			return;
		}
		;
		int radius = config.getInt("searchfor." + material + ".checkradius");
		int allowednumberofblocks = config.getInt("searchfor." + material + ".allowednumberofblocks");
		int blockCounter = 1;
		int minX = block.getX() - radius;
		int minY = block.getY() - radius;
		int minZ = block.getZ() - radius;
		int maxX = block.getX() + radius;
		int maxY = block.getY() + radius;
		int maxZ = block.getZ() + radius;
		World world = player.getWorld();
		for(int x = minX; x <= maxX; x++) {
			for(int y = minY; y <= maxY; y++) {
				for(int z = minZ; z <= maxZ; z++) {
					if(material.equalsIgnoreCase(world.getBlockAt(x, y, z).getType().toString())) {
						if(blockCounter > allowednumberofblocks) {
							player.sendMessage(ChatColor.RED + "It's not allowed to place more than " + allowednumberofblocks + " blocks of "
									+ material + " in a radius of " + radius + ".");
							if(config.getBoolean("general.warnonconsole") == true) {
								Bukkit.getLogger().info(
										"[" + plugin.getName().toString() + "] " + player.getName() + " tried to set more than "
												+ allowednumberofblocks + " blocks of " + material + " in a radius of " + radius + ".");
							}
							;
							event.setCancelled(true);
							return;
						}
						blockCounter++;
					}
				}
			}
		}
	}
}
