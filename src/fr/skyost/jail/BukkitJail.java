package fr.skyost.jail;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import fr.skyost.jail.listeners.CommandsExecutor;
import fr.skyost.jail.listeners.EventsListener;
import fr.skyost.jail.tasks.ReleasePlayer;
import fr.skyost.jail.utils.MetricsLite;
import fr.skyost.jail.utils.Skyupdater;
import fr.skyost.jail.utils.Utils;

public class BukkitJail extends JavaPlugin {
	
	private static ConfigFile config;
	
	public void onEnable() {
		if(!init()) {
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	
	private final boolean init() {
		try {
			Bukkit.getPluginManager().registerEvents(new EventsListener(), this);
			config = new ConfigFile(this);
			config.init();
			if(Bukkit.getWorld(config.Jail_World) == null) {
				Bukkit.createWorld(new WorldCreator(config.Jail_World));
			}
			CommandExecutor executor = new CommandsExecutor();
			this.getCommand("jail").setExecutor(executor);
			this.getCommand("unjail").setExecutor(executor);
			this.getCommand("setjail").setExecutor(executor);
			for(Entry<String, Integer> entry : config.JailedData.entrySet()) {
				if(entry.getValue() != -1) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + entry.getKey() + " will be released in " + Utils.roundTime(entry.getValue()) + " " + Utils.getTimeUnit(entry.getValue()) + " !");
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new ReleasePlayer(entry.getKey()), entry.getValue() * 20);
				}
			}
			if(config.CheckForUpdates) {
				new Skyupdater(this, 66389, this.getFile(), true, true);
			}
			new MetricsLite(this).start();
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
	
}
