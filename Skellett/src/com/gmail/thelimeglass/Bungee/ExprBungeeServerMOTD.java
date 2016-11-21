package com.gmail.thelimeglass.Bungee;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketGetMOTDServer;

public class ExprBungeeServerMOTD extends SimpleExpression<String>{
	
	//[skellett[cord]] [get] MOTD of [server] %string%
	
	private Expression<String> server;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
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
		return "[skellett[cord]] [get] MOTD of [server] %string%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		PacketGetMOTDServer packet = new PacketGetMOTDServer(server.getSingle(e));
		Object obj = packet.send();
		String MOTD = (String) obj;
		return new String[]{MOTD};
	}
}