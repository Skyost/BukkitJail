package com.skyost.jail.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.skyost.jail.BukkitJail;
import com.skyost.jail.tasks.ReleasePlayer;
import com.skyost.jail.util.Utils;

public class CommandsExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length >= 1) {
			Player player = Bukkit.getPlayer(args[0]);
			String message;
			if(cmd.getName().equalsIgnoreCase("jail")) {
				if(BukkitJail.getBukkitJailConfig().JailedData.get(args[0]) == null) {
					try {
						BukkitJail.getBukkitJailConfig().JailedData.put(args[0], -1);
						message = BukkitJail.getBukkitJailConfig().JailedMessages_1.replaceAll("/player/", args[0]);
						sender.sendMessage(message);
						if(player != null && player.isOnline()) {
							player.getInventory().setContents(new ItemStack[] {
								new ItemStack(Material.AIR)	
							});
							player.setGameMode(BukkitJail.getBukkitJailConfig().Jail_GameMode);
							player.teleport(new Location(Bukkit.getWorld(BukkitJail.getBukkitJailConfig().Jail_World), BukkitJail.getBukkitJailConfig().Jail_X, BukkitJail.getBukkitJailConfig().Jail_Y, BukkitJail.getBukkitJailConfig().Jail_Z, BukkitJail.getBukkitJailConfig().Jail_Yaw, BukkitJail.getBukkitJailConfig().Jail_Pitch));
							player.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_2);
						}
						BukkitJail.getBukkitJailConfig().save();
						if(args.length == 2) {
							if(Utils.isNumeric(args[1])) {
								int time = Integer.parseInt(args[1]);
								message = BukkitJail.getBukkitJailConfig().JailedMessages_10.replaceAll("/n/", String.valueOf(Utils.roundTime(time)));
								message = message.replaceAll("/u/", Utils.getTimeUnit(time));
								if(player != null && player.isOnline()) {
									player.sendMessage(message);
								}
								BukkitJail.getBukkitJailConfig().JailedData.put(args[0], time);
								Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("BukkitJail"), new ReleasePlayer(args[0]), time * 20);
							}
							else {
								message = BukkitJail.getBukkitJailConfig().JailedMessages_9.replaceAll("/player/", sender.getName());
								message = message.replaceAll("/reason/", args[1]);
								if(player != null && player.isOnline()) {
									player.sendMessage(message);
								}
							}
							BukkitJail.getBukkitJailConfig().save();
							return true;
						}
						if(args.length >= 2) {
							message = BukkitJail.getBukkitJailConfig().JailedMessages_9.replaceAll("/player/", sender.getName());
							if(Utils.isNumeric(args[args.length - 1])) {
								String reason = Utils.ArrayToStr(args, args.length - 1);
								message = message.replaceAll("/reason/", reason.substring(args[0].length() + 1));
								if(player != null && player.isOnline()) {
									player.sendMessage(message);
								}
								int time = Integer.parseInt(args[args.length - 1]);
								message = BukkitJail.getBukkitJailConfig().JailedMessages_10.replaceAll("/n/", String.valueOf(Utils.roundTime(time)));
								message = message.replaceAll("/u/", Utils.getTimeUnit(time));
								if(player != null && player.isOnline()) {
									player.sendMessage(message);
								}
								BukkitJail.getBukkitJailConfig().JailedData.put(args[0], time);
								Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("BukkitJail"), new ReleasePlayer(args[0]), time * 20);
							}
							else {
								String reason = Utils.ArrayToStr(args, args.length - 1);
								message = message.replaceAll("/reason/", reason.substring(args[0].length() + 1));
								if(player != null && player.isOnline()) {
									player.sendMessage(message);
								}
							}
							BukkitJail.getBukkitJailConfig().save();
							return true;
						}
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				else {
					sender.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_3);
				}
			}
			else if(cmd.getName().equalsIgnoreCase("unjail")) {
				if(BukkitJail.getBukkitJailConfig().JailedData.get(args[0]) != null) {
					try {
						BukkitJail.getBukkitJailConfig().JailedData.remove(args[0]);
						message = BukkitJail.getBukkitJailConfig().JailedMessages_4.replaceAll("/player/", args[0]);
						sender.sendMessage(message);
						if(player != null && player.isOnline()) {
							player.setGameMode(Bukkit.getDefaultGameMode());
							player.teleport(Utils.getMainWorld().getSpawnLocation());
							player.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_5);
						}
						BukkitJail.getBukkitJailConfig().save();
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				else {
					sender.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_6);
				}
			}
		}
		else {
			if(cmd.getName().equalsIgnoreCase("setjail")) {
				if(sender instanceof Player) {
					try {
						Player player = (Player)sender;
						Location loc = player.getLocation();
						BukkitJail.getBukkitJailConfig().Jail_World = loc.getWorld().getName();
						BukkitJail.getBukkitJailConfig().Jail_X = loc.getBlockX();
						BukkitJail.getBukkitJailConfig().Jail_Y = loc.getBlockY();
						BukkitJail.getBukkitJailConfig().Jail_Z = loc.getBlockZ();
						BukkitJail.getBukkitJailConfig().Jail_Yaw = Math.round(loc.getYaw());
						BukkitJail.getBukkitJailConfig().Jail_Pitch = Math.round(loc.getPitch());
						sender.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_11);
						BukkitJail.getBukkitJailConfig().save();
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "Please do this from the game !");
				}
			}
			else {
				sender.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_7);
			}
		}
		return true;
	}

}
