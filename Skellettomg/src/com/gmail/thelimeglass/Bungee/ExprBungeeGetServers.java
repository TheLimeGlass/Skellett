package com.gmail.thelimeglass.Bungee;

import java.util.ArrayList;
import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketGetServers;

public class ExprBungeeGetServers extends SimpleExpression<String>{
	
	//[(the|all)] [of] [the] [skellett[cord]] [bungee[ ][cord]] servers
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(the|all)] [of] [the] [skellett[cord]] [bungee[ ][cord]] servers";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		PacketGetServers packet = new PacketGetServers();
		Object obj = packet.send();
		@SuppressWarnings("unchecked")
		ArrayList<String> servers = (ArrayList<String>) obj;
		return servers.toArray(new String[servers.size()]);
		
	}
}