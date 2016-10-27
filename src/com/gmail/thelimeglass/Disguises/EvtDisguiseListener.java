package com.gmail.thelimeglass.Disguises;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.gmail.thelimeglass.Skellett;

import me.libraryaddict.disguise.events.DisguiseEvent;

public class EvtDisguiseListener implements Listener {
	public EvtDisguiseListener (Plugin Skellett) {
		Skellett.getServer().getPluginManager().registerEvents((Listener)this, Skellett);
	}
	@EventHandler
	public void DisguiseEvent (DisguiseEvent e) {
		Bukkit.getConsoleSender().sendMessage(Skellett.cc("&cdisguise TEST"));
		EvtDisguise evt = new EvtDisguise(e.getEntity(), e.getDisguise());
		Bukkit.getServer().getPluginManager().callEvent((Event)evt);
		if (evt.isCancelled()) {
			e.setCancelled(true);
		}
	}
}
