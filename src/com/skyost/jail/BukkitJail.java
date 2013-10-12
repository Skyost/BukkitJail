package com.skyost.jail;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.skyost.jail.util.Updater;

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
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static final boolean isJailed(Player player) {
		if(config.JailedPlayers.contains(player.getName())) {
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
}
