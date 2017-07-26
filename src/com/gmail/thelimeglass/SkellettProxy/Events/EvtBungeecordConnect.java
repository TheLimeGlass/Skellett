package com.gmail.thelimeglass.SkellettProxy.Events;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtBungeecordConnect extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private UUID uuid;
	private OfflinePlayer player;

	public EvtBungeecordConnect(UUID uuid, OfflinePlayer player) {
		this.uuid =  uuid;
		this.player = player;
	}

	public UUID getUUID() {
		return uuid;
	}
	
	public OfflinePlayer getOfflinePlayer(){
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