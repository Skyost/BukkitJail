package fr.skyost.jail.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.World;

import fr.skyost.jail.BukkitJail;

public class Utils {

	public static final World getMainWorld() {
		try {
			Properties server = new Properties();
			server.load(new FileInputStream(new File("server.properties")));
			return Bukkit.getWorld((String)server.get("level-name"));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static final int roundTime(int time) {
		if(time < 60) {
			return time;
		}
		else if(time >= 60 && time < 3600) {
			return time / 60;
		}
		else if(time >= 3600 && time < 86400) {
			return time / 3600;
		}
		else {
			return time / 86400;
		}
	}
	
	public static final String getTimeUnit(int time) {
		if(time <= 1) {
			return BukkitJail.getBukkitJailConfig().TimeUnits.get(0);
		}
		else if(time < 60) {
			return BukkitJail.getBukkitJailConfig().TimeUnits.get(0) + "s";
		}
		else if(time == 60) {
			return BukkitJail.getBukkitJailConfig().TimeUnits.get(1);
		}
		else if(time > 60 && time < 3600) {
			return BukkitJail.getBukkitJailConfig().TimeUnits.get(1) + "s";
		}
		else if(time == 3600) {
			return BukkitJail.getBukkitJailConfig().TimeUnits.get(2);
		}
		else if(time > 3600 && time < 86400) {
			return BukkitJail.getBukkitJailConfig().TimeUnits.get(2) + "s";
		}
		else if(time == 86400) {
			return BukkitJail.getBukkitJailConfig().TimeUnits.get(3);
		}
		else {
			return BukkitJail.getBukkitJailConfig().TimeUnits.get(3) + "s";
		}
	}
	
	public static final String ArrayToStr(String[] array, int number) {
		String str = "";
		if(number < array.length) {
			for(int i = 0; i != number; i++) {
				str = str + array[i] + " ";
			}
			return str.substring(0, str.length() - 1);
		}
		return null;
	}
	
	public static final boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
		}
		catch(NumberFormatException ex) {
			return false;
		}
		return true;
	}
	
}
