package com.gmail.thelimeglass.Events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtDownload extends Event implements Cancellable {
	
	//[on] [skellett] download:
	
	private static final HandlerList handlers = new HandlerList();
	private boolean cancel = false;
	private String url;
	public EvtDownload(String url) {
		this.url = url;
	}
	public String getUrl() {
		return this.url;
	}
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
	public boolean isCancelled() {
		return this.cancel;
	}
	public void setCancelled(boolean c) {
		this.cancel = c;
	}
}
