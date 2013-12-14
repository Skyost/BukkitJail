package fr.skyost.jail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.plugin.Plugin;

import fr.skyost.jail.utils.Config;

public class ConfigFile extends Config {
	public ConfigFile(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
		CONFIG_HEADER = "BukkitJail Configuration";
		
		TimeUnits.add("second");
		TimeUnits.add("minute");
		TimeUnits.add("hour");
		TimeUnits.add("day");
	}
	
	public HashMap<String, Integer> JailedData = new HashMap<String, Integer>();
	public ArrayList<String> TimeUnits = new ArrayList<String>();
	
	public boolean CheckForUpdates = true;
	
	public GameMode Jail_GameMode = GameMode.CREATIVE;
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
	
	public String JailedMessages_1 = "§2/player/ is now jailed.";
	public String JailedMessages_2 = "§4Your are in the jail, don't cry !";
	public String JailedMessages_3 = "§4This player is already jailed.";
	public String JailedMessages_4 = "§2/player/ is now released.";
	public String JailedMessages_5 = "§6You have been released from the jail.";
	public String JailedMessages_6 = "§4This player is not jailed.";
	public String JailedMessages_7 = "§4You must specify at least one argument. Example : /jail <Player> or /release <Player>.";
	public String JailedMessages_8 = "§4Your are in the jail so you can't do this !";
	public String JailedMessages_9 = "§4You have been jailed by /player/ because /reason/.";
	public String JailedMessages_10 = "§4You are in the jail for /n/ /u/.";
	public String JailedMessages_11 = "§2The jail has been set to your current location !";
}
