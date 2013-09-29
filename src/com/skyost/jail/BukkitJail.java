package com.skyost.jail;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitJail extends JavaPlugin {
	
	private static ConfigFile config;
	private final static Properties server = new Properties();
	
	public void onEnable() {
		try {
			Bukkit.getPluginManager().registerEvents(new Listeners(), this);
			server.load(new FileInputStream(new File("server.properties")));
			config = new ConfigFile(this);
			config.init();
			if(Bukkit.getWorld(config.Jail_World) == null) {
				Bukkit.createWorld(new WorldCreator(config.Jail_World));
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
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
	
	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length >= 1) {
			Player player = Bukkit.getPlayer(args[0]);
			String message;
			if(cmd.getName().equalsIgnoreCase("jail")) {
				if(!config.JailedPlayers.contains(args[0])) {
					try {
						config.JailedPlayers.add(args[0]);
						config.save();
						message = config.JailedMessages_1.replaceAll("/player/", player.getName());
						sender.sendMessage(message);
						if(player != null && player.isOnline()) {
							player.teleport(new Location(Bukkit.getWorld(config.Jail_World), config.Jail_X, config.Jail_Y, config.Jail_Z, config.Jail_Yaw, config.Jail_Pitch));
							sender.sendMessage(config.JailedMessages_2);
						}
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				else {
					sender.sendMessage(config.JailedMessages_3);
				}
			}
			else if(cmd.getName().equalsIgnoreCase("release")) {
				if(config.JailedPlayers.contains(args[0])) {
					try {
						config.JailedPlayers.remove(args[0]);
						config.save();
						message = config.JailedMessages_4.replaceAll("/player/", player.getName());
						sender.sendMessage(message);
						if(player != null || player.isOnline()) {
							player.teleport(Bukkit.getWorld((String)server.get("level-name")).getSpawnLocation());
							sender.sendMessage(config.JailedMessages_5);
						}
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				else {
					sender.sendMessage(config.JailedMessages_6);
				}
			}
		}
		else {
			sender.sendMessage(config.JailedMessages_7);
		}
		return true;
	}
}
