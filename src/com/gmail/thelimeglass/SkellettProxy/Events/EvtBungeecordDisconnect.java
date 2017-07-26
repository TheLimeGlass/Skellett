package com.gmail.thelimeglass.SkellettProxy.Events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtBungeecordDisconnect extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private UUID uuid;
	private String player;
	private OfflinePlayer offlineplayer;

	public EvtBungeecordDisconnect(UUID uuid, String player) {
		this.uuid =  uuid;
		this.player = player;
		this.offlineplayer = Bukkit.getOfflinePlayer(uuid);
	}

	public UUID getUUID() {
		return uuid;
	}
	
	public String getPlayerName(){
		return player;
	}
	
	public OfflinePlayer getOfflinePlayer(){
		return offlineplayer;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}