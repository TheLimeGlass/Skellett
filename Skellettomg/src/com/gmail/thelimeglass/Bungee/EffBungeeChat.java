package com.gmail.thelimeglass.Bungee;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketChat;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

public class EffBungeeChat extends Effect {

	//[skellett[cord]] (force|make) %string% [to] (say|chat|(run|execute)[ command]) %string% [on bungee[ ][cord]]
	
	private Expression<String> msg;
	private Expression<String> uuid;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		uuid = (Expression<String>) e[0];
		msg = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett[cord]] (force|make) %string% [to] (say|chat|(run|execute)[ command]) %string% [on bungee[ ][cord]]";
	}
	@Override
	protected void execute(Event e) {
		UUID uniqueId;
        try {
            uniqueId = UUID.fromString(uuid.getSingle(e));
        } catch (IllegalArgumentException ex) {
            return;
        }
		PacketChat packet = new PacketChat(uniqueId, msg.getSingle(e));
		packet.send();
	}
}
