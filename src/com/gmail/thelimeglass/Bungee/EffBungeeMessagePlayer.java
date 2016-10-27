package com.gmail.thelimeglass.Bungee;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketMessagePlayer;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

public class EffBungeeMessagePlayer extends Effect {

	//[skellett[cord]] (message|send|msg) %string% to bungee[ ][cord] [player] %string%
	
	private Expression<String> message;
	private Expression<String> uuid;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		message = (Expression<String>) e[0];
		uuid = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett[cord]] (message|send|msg) %string% to bungee[[ ][cord]] [player] %string%";
	}
	@Override
	protected void execute(Event e) {
		UUID uniqueId;
        try {
            uniqueId = UUID.fromString(uuid.getSingle(e));
        } catch (IllegalArgumentException ex) {
            return;
        }
        PacketMessagePlayer packet = new PacketMessagePlayer(uniqueId, message.getSingle(e));
        packet.send();
	}
}
