package net.mahagon.ReduceUnpopularBlocks.Commands;

import java.util.Map;
import java.util.Set;

import net.mahagon.ReduceUnpopularBlocks.ReduceUnpopularBlocksPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginDescriptionFile;

public class ReduceUnpopularBlocksCommandExecutor implements CommandExecutor
{
	private ReduceUnpopularBlocksPlugin plugin;

	public ReduceUnpopularBlocksCommandExecutor(ReduceUnpopularBlocksPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		try {
			if(args.length < 1) {
				throw new IllegalArgumentException("must be more than one");
			}
			PluginCommand pluginCmd = Bukkit.getServer().getPluginCommand(cmdLabel + " " + args[0]);

			if(!pluginCmd.testPermissionSilent(sender)) {
				sender.sendMessage(ChatColor.RED + "[" + plugin.getName().toString() + "] You don't have permission.");
				return true;
			}

			return pluginCmd.execute(sender, cmdLabel, args);
		}catch(Exception e) {
			PluginDescriptionFile description = plugin.getDescription();
			sender.sendMessage(ChatColor.GOLD + "[" + plugin.getName().toString() + "] Version " + description.getVersion());
			Map<String, Map<String, Object>> customers = description.getCommands();
			Set<String> keys = customers.keySet();
			for(String singleKey : keys) {
				sender.sendMessage(ChatColor.GOLD + "  " + customers.get(singleKey).get("description"));
				sender.sendMessage(ChatColor.GOLD + "  - usage: " + ChatColor.WHITE + customers.get(singleKey).get("usage"));
				sender.sendMessage(ChatColor.GOLD + "  - permission: " + ChatColor.WHITE + customers.get(singleKey).get("permission"));
				sender.sendMessage(ChatColor.GOLD + " ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ");
			}
			return true;
		}
	}
}
