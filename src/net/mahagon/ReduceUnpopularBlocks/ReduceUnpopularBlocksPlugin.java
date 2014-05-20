package net.mahagon.ReduceUnpopularBlocks;

import net.mahagon.ReduceUnpopularBlocks.Commands.ReduceUnpopularBlocksAddCommandExecutor;
import net.mahagon.ReduceUnpopularBlocks.Commands.ReduceUnpopularBlocksCommandExecutor;
import net.mahagon.ReduceUnpopularBlocks.Commands.ReduceUnpopularBlocksDelCommandExecutor;
import net.mahagon.ReduceUnpopularBlocks.Commands.ReduceUnpopularBlocksListCommandExecutor;
import net.mahagon.ReduceUnpopularBlocks.Commands.ReduceUnpopularBlocksReloadCommandExecutor;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ReduceUnpopularBlocksPlugin extends JavaPlugin
{
	private FileConfiguration config = getConfig();

	@Override
	public void onEnable() {
		config.addDefault("general.warnonconsole", true);
		if(config.getConfigurationSection("searchfor") == null) {
			config.addDefault("searchfor.DAYLIGHT_DETECTOR.activate", true);
			config.addDefault("searchfor.DAYLIGHT_DETECTOR.checkradius", 1);
			config.addDefault("searchfor.DAYLIGHT_DETECTOR.allowednumberofblocks", 1);
			config.addDefault("searchfor.HOPPER.activate", true);
			config.addDefault("searchfor.HOPPER.checkradius", 5);
			config.addDefault("searchfor.HOPPER.allowednumberofblocks", 4);
			config.addDefault("searchfor.BEACON.activate", true);
			config.addDefault("searchfor.BEACON.checkradius", 3);
			config.addDefault("searchfor.BEACON.allowednumberofblocks", 1);
		}
		config.options().copyDefaults(true);
		saveConfig();

		this.getCommand("rub").setExecutor(new ReduceUnpopularBlocksCommandExecutor(this));
		this.getCommand("rub add").setExecutor(new ReduceUnpopularBlocksAddCommandExecutor(this));
		this.getCommand("rub list").setExecutor(new ReduceUnpopularBlocksListCommandExecutor(this));
		this.getCommand("rub del").setExecutor(new ReduceUnpopularBlocksDelCommandExecutor(this));
		this.getCommand("rub reload").setExecutor(new ReduceUnpopularBlocksReloadCommandExecutor(this));

		BlockListener blockListener = new BlockListener(this);
		Bukkit.getServer().getPluginManager().registerEvents(blockListener, this);
		HangingBlockListener hangingBlockListener = new HangingBlockListener(this);
		Bukkit.getServer().getPluginManager().registerEvents(hangingBlockListener, this);
	}

}
