package dev.feriixu.HourOfThePhantom.Runnables;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dev.feriixu.HourOfThePhantom.HOTP;

public class BeginHOTP extends BukkitRunnable
{
	private World world;
	private HOTP hotp;
	
	public BeginHOTP(World world, HOTP hotp)
	{
		this.world = world;
		this.hotp = hotp;
	}

	@Override
	public void run()
	{
		for (Player player : world.getPlayers())
		{
			player.sendMessage(ChatColor.DARK_PURPLE + "The hour of the Phantom has begun!");
			player.playSound(player.getLocation(), Sound.ENTITY_PHANTOM_FLAP, 1, 1);
			hotp.allowPhantoms = true;
		}
	}
}
