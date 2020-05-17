package dev.feriixu.HourOfThePhantom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Phantom;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import dev.feriixu.HourOfThePhantom.Runnables.BeginHOTP;
import dev.feriixu.HourOfThePhantom.Runnables.EndHOTP;

public class HOTP
{
	private static Plugin plugin = Main.getPlugin(Main.class);
	private static Map<UUID, HOTP> tasks = new HashMap<UUID, HOTP>();

	private ArrayList<Phantom> phantoms = new ArrayList<Phantom>();
	private BukkitTask startTask;
	private BukkitTask endTask;
	public boolean allowPhantoms = false;

	public HOTP(World world, long timeToStart, long timeToEnd)
	{
		this.startTask = (BukkitTask) new BeginHOTP(world, this).runTaskTimer(plugin, timeToStart, 24000);
		this.endTask = (BukkitTask) new EndHOTP(world, this).runTaskTimer(plugin, timeToEnd, 24000);
		if (timeToStart < timeToEnd) // Determine if spawns should be allowed initially
		{
			this.allowPhantoms = false;
		} else
		{
			this.allowPhantoms = true;
		}
	}

	public void SetTasks(BukkitTask startTask, BukkitTask endTask)
	{
		this.startTask = startTask;
		this.endTask = endTask;
	}

	public void Cancel()
	{
		this.startTask.cancel();
		this.startTask = null;
		this.endTask.cancel();
		this.endTask = null;
		this.killPhantoms();
		DebugMessage.Broadcast("Cancelled task.");
	}

	public static boolean GetAllowPhantoms(UUID world)
	{
		if (tasks.containsKey(world))
		{
			return tasks.get(world).allowPhantoms;
		} else
		{
			return true;
		}
	}

	public static void Schedule(World world, long offset, String reason, boolean notify)
	{
		// Read the config
		FileConfiguration config = plugin.getConfig();
		List<String> worlds = config.getStringList("Worlds");

		DebugMessage.Broadcast("Scheduling Reason: " + ChatColor.YELLOW + reason);

		// If the world isn't in the config, don't schedule and return.
		if (!worlds.contains(world.getName()))
		{
			DebugMessage.Broadcast("World " + ChatColor.YELLOW + world.getName() + ChatColor.WHITE
					+ " is not in the config, not scheduling.");
			return;
		}

		// Debug messages
		DebugMessage.Broadcast("Starting scheduler for " + ChatColor.YELLOW + world.getName());

		// Cancel old tasks
		if (tasks.containsKey(world.getUID()))
		{
			DebugMessage.Broadcast("Found old task, cancelling...");
			tasks.get(world.getUID()).Cancel();
		}

		// Calculate delays
		long time = (world.getTime() + offset) % 24000;
		long startTime = config.getInt("StartTime", 17000);
		long endTime = config.getInt("EndTime", 19000);
		long timeToStart = 0;
		long timeToEnd = 0;

		if (time < startTime)
		{
			timeToStart = startTime - time;
		} else
		{
			timeToStart = startTime + 24000 - time;
		}

		if (time < endTime)
		{
			timeToEnd = endTime - time;
		} else
		{
			timeToEnd = endTime + 24000 - time;
		}

		// Debug Messages
		DebugMessage.Broadcast("Time: " + time);
		DebugMessage.Broadcast("StartTime: " + startTime);
		DebugMessage.Broadcast("EndTime: " + endTime);
		DebugMessage.Broadcast("TimeToStart: " + timeToStart);
		DebugMessage.Broadcast("TimeToEnd: " + timeToEnd);

		// Create new task
		tasks.put(world.getUID(), new HOTP(world, timeToStart, timeToEnd));

		DebugMessage.Broadcast(ChatColor.GRAY + "Done scheduling for " + ChatColor.YELLOW + world.getName());
	}

	public static void AddPhantom(Phantom phantom)
	{
		DebugMessage.Broadcast("Trying to add phantom to list.");
		if (tasks.containsKey(phantom.getWorld().getUID()))
		{
			DebugMessage.Broadcast("World GUID found in list. Adding phantom to list.");
			ArrayList<Phantom> phantoms = tasks.get(phantom.getWorld().getUID()).phantoms;
			phantoms.add(phantom);
			DebugMessage.Broadcast(
					"Phantom list contains " + tasks.get(phantom.getWorld().getUID()).phantoms.size() + " phantoms.");
		} else
		{
			DebugMessage.Broadcast("World GUID NOT found in list.");
		}
	}

	public static void RemovePhantom(Phantom phantom)
	{
		if (tasks.containsKey(phantom.getWorld().getUID()))
		{
			tasks.get(phantom.getWorld().getUID()).phantoms.remove(phantom);
		}
	}

	public void killPhantoms()
	{
		DebugMessage.Broadcast("Killing " + phantoms.size() + " phantoms.");
		for (int i = 0; i < phantoms.size(); i++)
		{
			phantoms.get(i).remove();
		}
	}
}
