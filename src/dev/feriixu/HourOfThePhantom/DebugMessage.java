package dev.feriixu.HourOfThePhantom;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class DebugMessage
{
	private static Plugin plugin = Main.getPlugin(Main.class);

	public static void Broadcast(String message)
	{
		// Read the config
		FileConfiguration config = plugin.getConfig();
		if (config.getBoolean("DebugMessages"))
			Bukkit.broadcast(message, Server.BROADCAST_CHANNEL_ADMINISTRATIVE);
	}
}
