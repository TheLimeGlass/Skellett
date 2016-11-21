package com.gmail.thelimeglass.Bungee;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketMessageAllPlayers;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;

public class EffBungeeMessageAllPlayers extends Effect {

	//[skellett[cord]] (message|send|msg) %string% to [all] bungee[[ ][cord]] players
	
	private Expression<String> msg;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		msg = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett[cord]] (message|send|msg) %string% to [all] bungee[[ ][cord]] players";
	}
	@Override
	protected void execute(Event e) {
		PacketMessageAllPlayers packet = new PacketMessageAllPlayers(Skellett.cc(msg.getSingle(e)));
		packet.send();
	}
}
