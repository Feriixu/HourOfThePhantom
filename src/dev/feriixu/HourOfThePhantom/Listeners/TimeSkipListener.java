package dev.feriixu.HourOfThePhantom.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;

import dev.feriixu.HourOfThePhantom.HOTP;

public class TimeSkipListener implements Listener
{
	@EventHandler
	private void OnTimeSkip(TimeSkipEvent event)
	{
		HOTP.Schedule(event.getWorld(), event.getSkipAmount(), event.getSkipReason().toString(), false);
	}
}
