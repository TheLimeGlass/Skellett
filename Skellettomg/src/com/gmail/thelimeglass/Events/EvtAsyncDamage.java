package com.gmail.thelimeglass.Events;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtAsyncDamage extends Event implements Cancellable {
	
	//[on] (smashhit|async) damage:
	
	private static final HandlerList handlers = new HandlerList();
	private boolean cancel = false;
	private Player attacker;
	private Entity victim;
	private World world;
	private Location location;
	private Number damage;
	public EvtAsyncDamage(Entity victim, World world, Player attacker, Location location, Number damage) {
		this.victim = victim;
		this.world = world;
		this.attacker = attacker;
		this.location = location;
		this.damage = damage;
	}
	public Entity getVictim() {
		return this.victim;
	}
	public World getWorld() {
		return this.world;
	}
	public Player getAttacker() {
		return this.attacker;
	}
	public Number getDamage() {
		return this.damage;
	}
	public Location getLocation() {
		return this.location;
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
	public void setDamage(Number n) {
		this.damage = n;
	}
}
