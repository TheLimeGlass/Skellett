package com.gmail.thelimeglass.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.thelimeglass.Utils.Packets.PacketWrapper;

public class EvtPacket extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private String name;
	private Player player;
	private PacketWrapper packet;
	private Boolean cancelled = false;

	public EvtPacket(Player player, PacketWrapper packet) {
		this.player = player;
		this.name = packet.getName();
		this.packet = packet;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String getName() {
		return name;
	}
	
	public PacketWrapper getPacket() {
		return packet;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
