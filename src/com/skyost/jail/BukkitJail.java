package com.skyost.jail;

import java.io.IOException;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import com.skyost.jail.tasks.ReleasePlayer;
import com.skyost.jail.util.Updater;
import com.skyost.jail.util.Metrics;
import com.skyost.jail.util.Utils;
import com.skyost.jail.util.Metrics.Graph;

public class BukkitJail extends JavaPlugin {
	
	private static ConfigFile config;
	
	public void onEnable() {
		if(!init()) {
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	
	private final boolean init() {
		try {
			Bukkit.getPluginManager().registerEvents(new Listeners(), this);
			config = new ConfigFile(this);
			config.init();
			if(Bukkit.getWorld(config.Jail_World) == null) {
				Bukkit.createWorld(new WorldCreator(config.Jail_World));
			}
			if(config.CheckForUpdates) {
				new Updater(this, 66389, this.getFile(), Updater.UpdateType.DEFAULT, true);
			}
			Commands executor = new Commands();
			this.getCommand("jail").setExecutor(executor);
			this.getCommand("release").setExecutor(executor);
			this.getCommand("setjail").setExecutor(executor);
			startMetrics();
			for(Entry<String, Integer> entry : config.JailedData.entrySet()) {
				if(entry.getValue() != -1) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + entry.getKey() + " will be released in " + String.valueOf(Utils.roundTime(entry.getValue())) + " " + String.valueOf(Utils.getTimeUnit(entry.getValue())+ " !"));
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new ReleasePlayer(entry.getKey()), entry.getValue() * 20);
				}
			}
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static final boolean isJailed(String player) {
		if(config.JailedData.get(player) != null) {
			return true;
		}
		return false;
	}
	
	public static final ConfigFile getBukkitJailConfig() {
		return config;
	}
	
	public static final Location getJailLocation() {
		return new Location(Bukkit.getWorld(config.Jail_World), config.Jail_X, config.Jail_Y, config.Jail_Z, config.Jail_Yaw, config.Jail_Pitch);
	}
	
	private void startMetrics() {
		try {
		    Metrics metrics = new Metrics(this);
		    Graph checkUpdatesGraph = metrics.createGraph("checkUpdatesGraph");
		    checkUpdatesGraph.addPlotter(new Metrics.Plotter("Checking for Updates") {  
		    @Override
		    public int getValue() {  
		    	return 1;
		    }
		    
		    @Override
		    public String getColumnName() {
		    	if(config.CheckForUpdates) {
		    		return "Yes";
		    	}
		    	else if(!config.CheckForUpdates) {
		    		return "No";
		    	}
		    	else {
		    		return "Maybe";
		    	}
		    }
		    });
    		Graph worldsNumbersGraph = metrics.createGraph("jailedPlayersNumber");
    		worldsNumbersGraph.addPlotter(new Metrics.Plotter("Number of jailed players") {	
    			@Override
    			public int getValue() {	
    				return config.JailedData.size();
    			}
    		});
		    metrics.start();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
