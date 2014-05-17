package net.mahagon.ReduceUnpopularBlocks.Commands;

import net.mahagon.ReduceUnpopularBlocks.ReduceUnpopularBlocksPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

public class ReduceUnpopularBlocksAddCommandExecutor implements CommandExecutor
{
	private ReduceUnpopularBlocksPlugin plugin;

	public ReduceUnpopularBlocksAddCommandExecutor(ReduceUnpopularBlocksPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		try {
			if(args.length == 4) {
				int checkradius;
				int allowednumberofblocks;
				Material matchmaterial = Material.matchMaterial(args[1]);
				if(matchmaterial == null) {
					throw new IllegalArgumentException("Material not found.");
				}
				String material = matchmaterial.toString();
				try {
					checkradius = Integer.parseInt(args[2]);
				}catch(NumberFormatException e) {
					throw new IllegalArgumentException("Radius must be a Number.");
				}
				try {
					allowednumberofblocks = Integer.parseInt(args[3]);
				}catch(NumberFormatException e) {
					throw new IllegalArgumentException("The Amount of blocks must be a Number.");
				}
				FileConfiguration config = plugin.getConfig();
				config.options().configuration().set("searchfor." + material + ".activate", true);
				config.options().configuration().set("searchfor." + material + ".checkradius", checkradius);
				config.options().configuration().set("searchfor." + material + ".allowednumberofblocks", allowednumberofblocks);
				plugin.saveConfig();
				sender.sendMessage(ChatColor.GOLD + "[" + plugin.getName().toString() + "] " + material + " has been added.");
			}
			else {
				throw new IllegalArgumentException("Wrong number of arguments.");
			}
		}catch(IllegalArgumentException e) {
			sender.sendMessage(ChatColor.RED + "[" + plugin.getName().toString() + "] " + e.getMessage());
			PluginDescriptionFile description = plugin.getDescription();
			sender.sendMessage(ChatColor.GOLD + "  " + description.getCommands().get("rub " + args[0].toLowerCase()).get("description"));
			sender.sendMessage(ChatColor.GOLD + "  - usage: " + ChatColor.WHITE
					+ description.getCommands().get("rub " + args[0].toLowerCase()).get("usage"));
		}
		return true;
	}
}
