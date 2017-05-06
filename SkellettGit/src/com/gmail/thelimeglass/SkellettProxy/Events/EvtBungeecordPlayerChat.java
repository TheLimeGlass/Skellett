package com.gmail.thelimeglass.SkellettProxy.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtBungeecordPlayerChat extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	private String message;
	private Player player, receiver;

	public EvtBungeecordPlayerChat(Player player, String message, Player receiver) {
		cancelled = false;
		this.player =  player;
		this.message = message;
		this.receiver = receiver;
	}

	public Player getPlayer() {
		return player;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Player getReceiver() {
		return receiver;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}