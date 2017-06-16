package com.gmail.thelimeglass.SkellettProxy.Events;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtBungeecordCommand extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private UUID uuid;
	private String playerName, command;
	private OfflinePlayer offlineplayer;

	public EvtBungeecordCommand(String playerName, UUID uuid, OfflinePlayer offlinePlayer, String command) {
		this.uuid =  uuid;
		this.playerName = playerName;
		this.offlineplayer = offlinePlayer;
		this.command = command;
	}

	public UUID getUUID() {
		return uuid;
	}
	
	public String getCommand() {
		return command;
	}
	
	public String getPlayerName(){
		return playerName;
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