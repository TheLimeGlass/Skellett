package com.gmail.thelimeglass.Utils.Packets;

import org.bukkit.entity.Player;

import com.gmail.thelimeglass.Utils.ReflectionUtil;

import ch.njol.skript.Skript;

import java.lang.reflect.Field;

public class PacketWrapper {

	protected Object packet;

	public PacketWrapper(Object packet) {
		this.packet = packet;
	}

	public void setValue(String field, Object value) {
		try {
			Field f = this.packet.getClass().getDeclaredField(field);
			f.setAccessible(true);
			f.set(this.packet, value);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			Skript.error(field + " is not a field for packet " + getName());
		}
	}

	public Object getValue(String field) {
		try {
			Field f = this.packet.getClass().getDeclaredField(field);
			f.setAccessible(true);
			return f.get(this.packet);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			Skript.error(field + " is not a field for packet " + getName());
		}
		return null;
	}

	public void send(Player player) {
		try {
			ReflectionUtil.sendPacket(player, this.packet);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public Object getNMSPacket() {
		return this.packet;
	}

	public String getName() {
		return this.packet.getClass().getSimpleName();
	}
}