package com.gmail.thelimeglass.Bungee;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketGetPlayerUUID;

public class ExprBungeeUUID extends SimpleExpression<String>{
	
	//[skellett[cord]] [get] [player] bungee[[ ][cord]] uuid [of] %string%
	
	private Expression<String> player;
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
		player = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett[cord]] [get] [player] bungee[[ ][cord]] uuid [of] %string%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		PacketGetPlayerUUID packet = new PacketGetPlayerUUID(player.getSingle(e).toString());
		Object obj = packet.send();
		String uuid = (String) obj.toString();
		return new String[]{uuid};
	}
}