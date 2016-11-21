package com.gmail.thelimeglass.Bungee;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketGetPlayersGlobal;

public class ExprBungeeGetPlayers extends SimpleExpression<String>{
	
	//[(the|all)] [of] [the] [skellett[cord]] [bungee[ ][cord]] players
	
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
		return "[(the|all)] [of] [the] [skellett[cord]] [bungee[ ][cord]] players";
	}
	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	protected String[] get(Event e) {
		PacketGetPlayersGlobal packet = new PacketGetPlayersGlobal();
		Object obj = packet.send();
		ArrayList<String> players = new ArrayList<>();
		for (UUID uuid : (List<UUID>) obj) {
			players.add(uuid.toString());
		}
		return players.toArray(new String[players.size()]);
		
	}
}