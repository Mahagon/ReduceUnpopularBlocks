package net.mahagon.ReduceUnpopularBlocks.Commands;

import net.mahagon.ReduceUnpopularBlocks.ReduceUnpopularBlocksPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class ReduceUnpopularBlocksReloadCommandExecutor implements CommandExecutor
{
	private ReduceUnpopularBlocksPlugin plugin;

	public ReduceUnpopularBlocksReloadCommandExecutor(ReduceUnpopularBlocksPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		try {
			if(args.length == 1) {
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.GOLD + "[" + plugin.getName() + "] Config has been reloaded.");
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
