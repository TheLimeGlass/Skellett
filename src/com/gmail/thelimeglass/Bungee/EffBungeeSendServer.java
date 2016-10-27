package com.gmail.thelimeglass.Bungee;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketConnectPlayer;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

public class EffBungeeSendServer extends Effect {

	//[skellett[cord]] (send|connect) %string% to [[bungee[ ][cord]] server] %string%
	
	private Expression<String> uuid;
	private Expression<String> server;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		uuid = (Expression<String>) e[0];
		server = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett[cord]] (send|connect) %string% to [[bungee[ ][cord]] server] %string%";
	}
	@Override
	protected void execute(Event e) {
		UUID uniqueId;
        try {
            uniqueId = UUID.fromString(uuid.getSingle(e));
        } catch (IllegalArgumentException ex) {
            return;
        }
        PacketConnectPlayer packet = new PacketConnectPlayer(uniqueId, server.getSingle(e));
		packet.send();
	}
}
