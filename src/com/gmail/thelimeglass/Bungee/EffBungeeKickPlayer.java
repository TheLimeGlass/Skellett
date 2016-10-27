package com.gmail.thelimeglass.Bungee;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketKickPlayer;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

public class EffBungeeKickPlayer extends Effect {

	//[skellett[cord]] kick %string% from bungee[ ][cord] (by reason of|because [of]|on account of|due to) %string%
	//[skellett[cord]] bungee[ ][cord] kick %string% (by reason of|because [of]|on account of|due to) %string%
	
	private Expression<String> uuid;
	private Expression<String> message;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		uuid = (Expression<String>) e[0];
		message = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett[cord]] kick %string% from bungee[ ][cord] (by reason of|because [of]|on account of|due to) %string%";
	}
	@Override
	protected void execute(Event e) {
		String msg = "Kicked by operator";
		if (message.getSingle(e) != null) {
			msg = message.getSingle(e);
		}
		UUID uniqueId;
		try {
			uniqueId = UUID.fromString(uuid.getSingle(e));
		} catch (IllegalArgumentException ex) {
			return;
		}
		PacketKickPlayer packet = new PacketKickPlayer(uniqueId, msg);
		packet.send();
	}
}
