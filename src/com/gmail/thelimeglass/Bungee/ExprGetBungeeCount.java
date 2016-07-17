package com.gmail.thelimeglass.Bungee;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketGetOnlineCountGlobal;

public class ExprGetBungeeCount extends SimpleExpression<Integer>{
	
	//[skellett[cord]] [get] online bungee[[ ]cord] players
	
	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett[cord]] [get] online bungee[[ ]cord] players";
	}
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		PacketGetOnlineCountGlobal packet = new PacketGetOnlineCountGlobal();
		Object obj = packet.send();
		Integer players = (Integer) obj;
		return new Integer[]{players};
	}
}