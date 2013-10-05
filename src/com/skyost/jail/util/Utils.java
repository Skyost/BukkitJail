package com.skyost.jail.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.skyost.jail.BukkitJail;

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
	
	public static final String getTime(int time) {
		if(time > 60 && time < 3600) {
			return BukkitJail.getBukkitJailConfig().TimeUnits.get(0);
		}
		else if(time > 60 && time < 3600) {
			return BukkitJail.getBukkitJailConfig().TimeUnits.get(1);
		}
		else {
			return BukkitJail.getBukkitJailConfig().TimeUnits.get(2);
		}
	}
	
	public static final String ArrayToStr(String[] array, int number) {
		String str = "";
		if(number < array.length) {
			for(int i = 0; i != number; i++) {
				str = str + array[i] + " ";
			}
			return str;
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
