package com.skyost.jail.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.skyost.jail.BukkitJail;
import com.skyost.jail.util.Utils;

public class EventsListener implements Listener {
	
	@EventHandler
	private final void onPlayerJoin(final PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(BukkitJail.isJailed(player.getName())) {	
			player.teleport(BukkitJail.getJailLocation());
			player.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_2);	
		}
		else {
			if(player.getWorld().getName().equals(BukkitJail.getBukkitJailConfig().Jail_World)) {
				if(!BukkitJail.isJailed(player.getName())) {
					player.setGameMode(Bukkit.getDefaultGameMode());
					player.teleport(Utils.getMainWorld().getSpawnLocation());
					player.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_5);
				}
			}
		}
	}
	
	@EventHandler
	private final void onPlayerRespawn(final PlayerRespawnEvent event) {
		if(BukkitJail.isJailed(event.getPlayer().getName())) {	
			event.getPlayer().teleport(BukkitJail.getJailLocation());
			event.getPlayer().sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_2);	
		}
	}
	
	@EventHandler
	private static final void onPlayerInteract(final PlayerInteractEvent event) {
		if(!BukkitJail.getBukkitJailConfig().JailedCanInteract) {
			if(BukkitJail.isJailed(event.getPlayer().getName())) {
				Action act = event.getAction();
				if(act == Action.LEFT_CLICK_AIR || act == Action.LEFT_CLICK_BLOCK || act == Action.RIGHT_CLICK_AIR || act == Action.RIGHT_CLICK_BLOCK) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
				}
			}
		}
	}
	
	@EventHandler
	private static final void onPlayerChat(final AsyncPlayerChatEvent event) {
		if(!BukkitJail.getBukkitJailConfig().JailedCanChat) {
			if(BukkitJail.isJailed(event.getPlayer().getName())) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
			}
		}
	}
	
	@EventHandler
	private static final void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
		if(!BukkitJail.getBukkitJailConfig().JailedCanUseCommand) {
			if(BukkitJail.isJailed(event.getPlayer().getName())) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
			}
		}
	}
	
	@EventHandler
	private static final void onPlayerMove(final PlayerMoveEvent event) {
		if(!BukkitJail.getBukkitJailConfig().JailedCanMove) {
			if(BukkitJail.isJailed(event.getPlayer().getName())) {
				event.setTo(event.getFrom());
				event.getPlayer().sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
			}
		}
	}
	
	@EventHandler
	private static final void onPlayerInventoryOpen(final InventoryOpenEvent event) {
		if(event.getPlayer() instanceof Player) {
			Player player = (Player)event.getPlayer();
			if(!BukkitJail.getBukkitJailConfig().JailedCanOpenInventory) {
				if(BukkitJail.isJailed(player.getName())) {
					event.setCancelled(true);
					player.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
				}
			}
		}
	}
	
}
