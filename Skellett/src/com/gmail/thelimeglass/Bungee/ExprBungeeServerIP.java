package com.gmail.thelimeglass.Bungee;

import java.net.InetSocketAddress;
import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketGetServerIP;

public class ExprBungeeServerIP extends SimpleExpression<InetSocketAddress>{
	
	//[skellett[cord]] [bungee[ ][cord]] server ip [address] of [server] %string%
	
	private Expression<String> server;
	@Override
	public Class<? extends InetSocketAddress> getReturnType() {
		return InetSocketAddress.class;
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
		return "[skellett[cord]] [bungee[ ][cord]] server ip [address] of [server] %string%";
	}
	@Override
	@Nullable
	protected InetSocketAddress[] get(Event e) {
		PacketGetServerIP packet = new PacketGetServerIP(server.getSingle(e));
		Object obj = packet.send();
		InetSocketAddress ip = (InetSocketAddress) obj;
		return new InetSocketAddress[]{ip};
	}
}