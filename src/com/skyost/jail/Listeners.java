package com.skyost.jail;

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

public class Listeners implements Listener {
	
	@EventHandler
	private static final void onPlayerJoin(PlayerJoinEvent event) {
		if(BukkitJail.isJailed(event.getPlayer())) {	
			event.getPlayer().teleport(BukkitJail.getJailLocation());
			event.getPlayer().sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_2);	
		}
	}
	
	@EventHandler
	private final static void onPlayerRespawn(PlayerRespawnEvent event) {
		if(BukkitJail.isJailed(event.getPlayer())) {	
			event.getPlayer().teleport(BukkitJail.getJailLocation());
			event.getPlayer().sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_2);	
		}
	}
	
	@EventHandler
	private static final void onPlayerInteract(PlayerInteractEvent event) {
		if(!BukkitJail.getBukkitJailConfig().JailedCanInteract) {
			if(BukkitJail.isJailed(event.getPlayer())) {
				Action act = event.getAction();
				if(act == Action.LEFT_CLICK_AIR || act == Action.LEFT_CLICK_BLOCK || act == Action.RIGHT_CLICK_AIR || act == Action.RIGHT_CLICK_BLOCK) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
				}
			}
		}
	}
	
	@EventHandler
	private static final void onPlayerChat(AsyncPlayerChatEvent event) {
		if(!BukkitJail.getBukkitJailConfig().JailedCanChat) {
			if(BukkitJail.isJailed(event.getPlayer())) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
			}
		}
	}
	
	@EventHandler
	private static final void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		if(!BukkitJail.getBukkitJailConfig().JailedCanUseCommand) {
			if(BukkitJail.isJailed(event.getPlayer())) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
			}
		}
	}
	
	@EventHandler
	private static final void onPlayerMove(PlayerMoveEvent event) {
		if(!BukkitJail.getBukkitJailConfig().JailedCanMove) {
			if(BukkitJail.isJailed(event.getPlayer())) {
				event.setTo(event.getFrom());
				event.getPlayer().sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
			}
		}
	}
	
	@EventHandler
	private static final void onPlayerInventoryOpen(InventoryOpenEvent event) {
		if(event.getPlayer() instanceof Player) {
			Player player = (Player)event.getPlayer();
			if(!BukkitJail.getBukkitJailConfig().JailedCanOpenInventory) {
				if(BukkitJail.isJailed(player)) {
					event.setCancelled(true);
					player.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
				}
			}
		}
	}
}
