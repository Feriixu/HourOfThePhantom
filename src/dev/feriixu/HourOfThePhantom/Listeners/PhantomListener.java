package dev.feriixu.HourOfThePhantom.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import dev.feriixu.HourOfThePhantom.DebugMessage;
import dev.feriixu.HourOfThePhantom.HOTP;

import org.bukkit.event.entity.EntityDeathEvent;

public class PhantomListener implements Listener
{
	@EventHandler
	public void OnCreatureSpawn(CreatureSpawnEvent event)
	{
		// Only check phantoms
		if (!event.getEntityType().equals(EntityType.PHANTOM))
			return;
		
		DebugMessage.Broadcast("Phantom spawn detected with source: " + ChatColor.YELLOW + event.getSpawnReason().toString());

		// Only cancel natural spawns
		if (!event.getSpawnReason().equals(SpawnReason.NATURAL))
			return;

		// If phantom spawning is not currently allowed
		boolean isAllowed = HOTP.GetAllowPhantoms(event.getLocation().getWorld().getUID());
		DebugMessage.Broadcast("Phantom spawn is " + isAllowed);
		if (isAllowed)
		{
			HOTP.AddPhantom((Phantom) event.getEntity());
		} else
		{
			DebugMessage.Broadcast("Denied phantom spawn.");
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void OnEntityDeath(EntityDeathEvent event)
	{
		// Only handle phantoms
		if (!(event.getEntity() instanceof Phantom))
			return;
		DebugMessage.Broadcast("Phantom despawn detected. Removing from list.");

		HOTP.RemovePhantom((Phantom) event.getEntity());
	}
}
