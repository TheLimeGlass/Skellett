package com.gmail.thelimeglass.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.frash23.smashhit.AsyncPreDamageEvent;

public class EvtAsyncDamageListener implements Listener {
	public EvtAsyncDamageListener (Plugin Skellett) {
		Skellett.getServer().getPluginManager().registerEvents((Listener)this, Skellett);
	}
	@EventHandler
	public void onAsyncPreDamageEvent (AsyncPreDamageEvent e) {
		Player damager = e.getDamager();
		EvtAsyncDamage evt = new EvtAsyncDamage(e.getEntity(), damager.getWorld(), damager, damager.getLocation(), e.getDamage());
		Bukkit.getServer().getPluginManager().callEvent((Event)evt);
		if (evt.isCancelled()) {
			e.setCancelled(true);
		}
	}
}
