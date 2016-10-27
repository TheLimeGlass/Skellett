package com.gmail.thelimeglass.Bungee;

import java.net.InetSocketAddress;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketGetPlayerIP;

public class ExprBungeePlayerIP extends SimpleExpression<InetSocketAddress>{
	
	//[skellett[cord]] [bungee[ ][cord]] player ip [address] of [uuid] %string%
	
	private Expression<String> uuid;
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
		uuid = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett[cord]] [bungee[ ][cord]] player ip [address] of [uuid] %string%";
	}
	@Override
	@Nullable
	protected InetSocketAddress[] get(Event e) {
		UUID uniqueId;
        try {
            uniqueId = UUID.fromString(uuid.getSingle(e));
        } catch (IllegalArgumentException ex) {
            return null;
        }
        PacketGetPlayerIP packet = new PacketGetPlayerIP(uniqueId);
		Object obj = packet.send();
		InetSocketAddress ip = (InetSocketAddress) obj;
		return new InetSocketAddress[]{ip};
	}
}