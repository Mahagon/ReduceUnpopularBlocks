package net.mahagon.ReduceUnpopularBlocks;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ReduceUnpopularBlocksPlugin extends JavaPlugin {
		private FileConfiguration config = getConfig();
		private static Plugin plugin;
	    /**
	     * Called on plugin enable.
	     */
	    @Override
		public void onEnable(){
	    	plugin = this;
	    	//Config -> create default values if not exist
	    	config.addDefault("general.warnonconsole",true);
	    	config.addDefault("searchfor.DAYLIGHT_DETECTOR.activate",true);
	    	config.addDefault("searchfor.DAYLIGHT_DETECTOR.checkradius",1);
	    	config.addDefault("searchfor.DAYLIGHT_DETECTOR.allowednumberofblocks",1);
	    	config.addDefault("searchfor.HOPPER.activate",true);
	    	config.addDefault("searchfor.HOPPER.checkradius",5);
	    	config.addDefault("searchfor.HOPPER.allowednumberofblocks",4);
	    	config.addDefault("searchfor.BEACON.activate",true);
	    	config.addDefault("searchfor.BEACON.checkradius",3);
	    	config.addDefault("searchfor.BEACON.allowednumberofblocks",1);
	    	config.options().copyDefaults(true);
	    	saveConfig();
	    	BlockListener blockListener = new BlockListener();
	    	Bukkit.getServer().getPluginManager().registerEvents(blockListener, this);
		}
	    /**
	     * Called on plugin disable.
	     */
	    @Override
		public void onDisable(){
	    	plugin = null;
		}
		/**
	     * Called on Command.
	     */
	    public static Plugin getPlugin() {
	    	return plugin;
	    }
	    

}
