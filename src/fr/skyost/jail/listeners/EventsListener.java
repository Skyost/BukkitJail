package fr.skyost.jail.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.skyost.jail.BukkitJail;
import fr.skyost.jail.utils.Utils;

public class EventsListener implements Listener {
	
	@EventHandler
	private final void onPlayerJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
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
		final Player player = event.getPlayer();
		if(BukkitJail.isJailed(player.getName())) {	
			player.teleport(BukkitJail.getJailLocation());
			player.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_2);	
		}
	}
	
	@EventHandler
	private final void onPlayerInteract(final PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		if(!BukkitJail.getBukkitJailConfig().JailedCan_Interact) {
			if(BukkitJail.isJailed(player.getName())) {
				final Action act = event.getAction();
				if(act == Action.LEFT_CLICK_AIR || act == Action.LEFT_CLICK_BLOCK || act == Action.RIGHT_CLICK_AIR || act == Action.RIGHT_CLICK_BLOCK) {
					event.setCancelled(true);
					player.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
				}
			}
		}
	}
	
	@EventHandler
	private final void onPlayerChat(final AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		if(!BukkitJail.getBukkitJailConfig().JailedCan_Chat) {
			if(BukkitJail.isJailed(player.getName())) {
				event.setCancelled(true);
				player.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
			}
		}
	}
	
	@EventHandler
	private final void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
		final Player player = event.getPlayer();
		if(!BukkitJail.getBukkitJailConfig().JailedCan_UseCommand) {
			if(BukkitJail.isJailed(player.getName())) {
				event.setCancelled(true);
				player.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
			}
		}
	}
	
	@EventHandler
	private final void onPlayerMove(final PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		if(!BukkitJail.getBukkitJailConfig().JailedCan_Move) {
			if(BukkitJail.isJailed(player.getName())) {
				event.setTo(event.getFrom());
				player.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
			}
		}
	}
	
	@EventHandler
	private final void onPlayerInventoryOpen(final InventoryOpenEvent event) {
		final HumanEntity human = event.getPlayer();
		if(human instanceof Player) {
			final Player player = (Player)human;
			if(!BukkitJail.getBukkitJailConfig().JailedCan_OpenInventory) {
				if(BukkitJail.isJailed(player.getName())) {
					event.setCancelled(true);
					player.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
				}
			}
		}
	}
	
	@EventHandler
	private final void onPlayerDropItem(final PlayerDropItemEvent event) {
		final Player player = event.getPlayer();
		if(!BukkitJail.getBukkitJailConfig().JailedCan_DropItem) {
			if(BukkitJail.isJailed(player.getName())) {
				event.setCancelled(true);
				player.sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_8);
			}
		}
	}
	
}
