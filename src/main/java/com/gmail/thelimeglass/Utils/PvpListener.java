package com.gmail.thelimeglass.Utils;

import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.thelimeglass.Skellett;

public class PvpListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if (Skellett.getInstance().config.getBoolean("Enable1_8pvp")) {
			event.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100.0D);
		} else {
			event.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.0D);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (Skellett.getInstance().config.getBoolean("Enable1_8pvp")) {
			event.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.0D);
		}
	}
}