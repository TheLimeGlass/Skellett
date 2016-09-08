package com.gmail.thelimeglass.Bungee;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketIsServerOnline;

public class ExprServerOnline extends SimpleExpression<Boolean>{
	
	//[skellett[cord]] [get] [(online|offline)] status of [bungee[ ][cord]] server %string%
	
	private Expression<String> server;
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		server = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett[cord]] [get] [(online|offline)] status of [bungee[ ][cord]] server %string%";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		PacketIsServerOnline packet = new PacketIsServerOnline(server.getSingle(e));
		Object obj = packet.send();
		Boolean state = (Boolean) obj;
		return new Boolean[]{state};
	}
}