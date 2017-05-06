package com.gmail.thelimeglass.SkellettProxy.Events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtBungeecordDisconnect extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private OfflinePlayer player;

	public EvtBungeecordDisconnect(OfflinePlayer player) {
		this.player =  player;
	}

	public OfflinePlayer getPlayer() {
		return player;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}