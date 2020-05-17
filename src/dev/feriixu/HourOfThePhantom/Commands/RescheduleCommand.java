package dev.feriixu.HourOfThePhantom.Commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import dev.feriixu.HourOfThePhantom.HOTP;
import dev.feriixu.HourOfThePhantom.Main;

public class RescheduleCommand implements CommandExecutor
{
	private Plugin plugin = Main.getPlugin(Main.class);

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3)
	{
		for (World world : plugin.getServer().getWorlds())
		{
			HOTP.Schedule(world, 0, "/rescheduleHOTP", true);
		}
		arg0.sendMessage("Rescheduled the tasks.");
		return true;
	}

}
