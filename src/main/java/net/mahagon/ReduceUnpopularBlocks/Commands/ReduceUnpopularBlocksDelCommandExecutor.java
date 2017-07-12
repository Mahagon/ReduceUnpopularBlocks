package net.mahagon.ReduceUnpopularBlocks.Commands;

import net.mahagon.ReduceUnpopularBlocks.ReduceUnpopularBlocksPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

public class ReduceUnpopularBlocksDelCommandExecutor implements CommandExecutor
{
	private ReduceUnpopularBlocksPlugin plugin;

	public ReduceUnpopularBlocksDelCommandExecutor(ReduceUnpopularBlocksPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		try {
			if(args.length == 2) {
				String material = Material.matchMaterial(args[1]).toString();
				if(material == null) {
					throw new IllegalArgumentException("Material not found.");
				}
				FileConfiguration config = plugin.getConfig();
				if(config.getConfigurationSection("searchfor." + material) == null) {
					sender.sendMessage(ChatColor.RED + "[" + plugin.getName() + "] " + material + " has already been deleted.");
					return true;
				}
				else {
					config.set("searchfor." + material, null);
					plugin.saveConfig();
					sender.sendMessage(ChatColor.GOLD + "[" + plugin.getName() + "] " + material + " has been deleted.");
				}
			}
			else {
				throw new IllegalArgumentException("Wrong number of arguments.");
			}
		}catch(IllegalArgumentException e) {
			sender.sendMessage(ChatColor.RED + "[" + plugin.getName() + "] " + e.getMessage());
			PluginDescriptionFile description = plugin.getDescription();
			sender.sendMessage(ChatColor.GOLD + "  " + description.getCommands().get("rub " + args[0].toLowerCase()).get("description"));
			sender.sendMessage(ChatColor.GOLD + "  - usage: " + ChatColor.WHITE
					+ description.getCommands().get("rub " + args[0].toLowerCase()).get("usage"));
		}
		return true;
	}

}
