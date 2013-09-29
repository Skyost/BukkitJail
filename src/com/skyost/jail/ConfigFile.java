package com.skyost.jail;

import java.io.File;
import java.util.ArrayList;
import org.bukkit.plugin.Plugin;

import com.skyost.jail.util.Config;

public class ConfigFile extends Config {
	public ConfigFile(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
		CONFIG_HEADER = "BukkitJail Configuration";
	}
	
	public ArrayList<String> JailedPlayers = new ArrayList<String>();
	
	public String Jail_World = "jail";
	public int Jail_X = 0;
	public int Jail_Y = 0;
	public int Jail_Z = 0;
	public int Jail_Yaw = 0;
	public int Jail_Pitch = 0;
	
	public boolean JailedCanInteract = false;
	public boolean JailedCanChat = true;
	public boolean JailedCanUseCommand = false;
	public boolean JailedCanMove = true;
	public boolean JailedCanOpenInventory = true;
	
	public String JailedMessages_1 = "�2/player/ is now jailed.";
	public String JailedMessages_2 = "�4Your are in the jail, don't cry !";
	public String JailedMessages_3 = "�4This player is already jailed.";
	public String JailedMessages_4 = "�2/player/ is now released.";
	public String JailedMessages_5 = "�6You have been released from the jail.";
	public String JailedMessages_6 = "�4This player is not jailed.";
	public String JailedMessages_7 = "�4You must specify at least one argument. Example : /jail <Player> or /release <Player>.";
	public String JailedMessages_8 = "�4Your are in the jail so you can't do this !";
}
