package com.gmail.thelimeglass.Disguises;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.libraryaddict.disguise.disguisetypes.Disguise;

public class EvtDisguise extends Event implements Cancellable {
	
	//[on] [[Libs]Disguises] disguise:
	
	private static final HandlerList handlers = new HandlerList();
	private boolean cancel = false;
	private Disguise disguise;
	private Entity entity;
	public EvtDisguise(Entity entity, Disguise disguise) {
		this.entity = entity;
		this.disguise = disguise;
	}
	public Disguise getDisguise() {
		return this.disguise;
	}
	public Entity getEntity() {
		return this.entity;
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
