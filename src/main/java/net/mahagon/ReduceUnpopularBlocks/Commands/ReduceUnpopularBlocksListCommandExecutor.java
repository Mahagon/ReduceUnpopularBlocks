package net.mahagon.ReduceUnpopularBlocks.Commands;

import net.mahagon.ReduceUnpopularBlocks.ReduceUnpopularBlocksPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

public class ReduceUnpopularBlocksListCommandExecutor implements CommandExecutor
{
	private ReduceUnpopularBlocksPlugin plugin;

	public ReduceUnpopularBlocksListCommandExecutor(ReduceUnpopularBlocksPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		try {
			if(args.length == 1) {
				FileConfiguration config = plugin.getConfig();
				for(String block : config.getConfigurationSection("searchfor").getKeys(false)) {
					sender.sendMessage(ChatColor.GOLD + block);
					sender.sendMessage(ChatColor.GOLD + "  * " + ChatColor.WHITE + "Active: "
							+ config.options().configuration().get("searchfor." + block + ".activate"));
					sender.sendMessage(ChatColor.GOLD + "  * " + ChatColor.WHITE + "Radius: "
							+ config.options().configuration().get("searchfor." + block + ".checkradius"));
					sender.sendMessage(ChatColor.GOLD + "  * " + ChatColor.WHITE + "Allowed number of blocks: "
							+ config.options().configuration().get("searchfor." + block + ".allowednumberofblocks"));
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
