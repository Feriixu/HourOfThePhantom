package dev.feriixu.HourOfThePhantom;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import dev.feriixu.HourOfThePhantom.Commands.RescheduleCommand;
import dev.feriixu.HourOfThePhantom.Listeners.PhantomListener;
import dev.feriixu.HourOfThePhantom.Listeners.TimeSkipListener;

public class Main extends JavaPlugin
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onDisable()
	{
		System.out.println(this.toString() + " disabled");
	}
	@Override
	public void onEnable()
	{
		loadConfig();
		this.getCommand("rescheduleHOTP").setExecutor(new RescheduleCommand());
		
		getServer().getPluginManager().registerEvents(new TimeSkipListener(), this);
		getServer().getPluginManager().registerEvents(new PhantomListener(), this);
		
		for (World world : getServer().getWorlds())
		{
			DebugMessage.Broadcast("World: " + world.getName() + " found on server.");
			HOTP.Schedule(world, 0, "Plugin Load", true);
		}
	}

	public void loadConfig()
	{
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}
