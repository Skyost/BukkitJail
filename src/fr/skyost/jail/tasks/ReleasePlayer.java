package fr.skyost.jail.tasks;

import org.bukkit.Bukkit;

import fr.skyost.jail.BukkitJail;
import fr.skyost.jail.utils.Utils;

public class ReleasePlayer implements Runnable {
	
	String playername;
	
	public ReleasePlayer(String playername) {
		this.playername = playername;
	}

	@Override
	public void run() {
		if(BukkitJail.isJailed(playername)) {
			try {
				BukkitJail.getBukkitJailConfig().JailedData.remove(playername);
				if(Bukkit.getPlayer(playername) != null && Bukkit.getPlayer(playername).isOnline()) {
					Bukkit.getPlayer(playername).setGameMode(Bukkit.getDefaultGameMode());
					Bukkit.getPlayer(playername).teleport(Utils.getMainWorld().getSpawnLocation());
					Bukkit.getPlayer(playername).sendMessage(BukkitJail.getBukkitJailConfig().JailedMessages_5);
				}
				BukkitJail.getBukkitJailConfig().save();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
