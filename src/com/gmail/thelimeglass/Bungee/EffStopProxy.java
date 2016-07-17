package com.gmail.thelimeglass.Bungee;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketStopProxy;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

public class EffStopProxy extends Effect {

	//[skellett[cord]] (stop|kill|end) [bungee[[ ][cord]] proxy [[with] (msg|string|text)] %string%
	
	private Expression<String> msg;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		msg = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett[cord]] (stop|kill|end) [bungee[[ ][cord]] proxy [[with] (msg|string|text)] %string%";
	}
	@Override
	protected void execute(Event e) {
		PacketStopProxy packet = new PacketStopProxy(msg.getSingle(e));
		packet.send();
	}
}
