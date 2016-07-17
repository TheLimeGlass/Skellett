package com.gmail.thelimeglass.Bungee;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketIsPlayerOnline;
import me.dommi2212.BungeeBridge.util.IsOnlineResult;

public class CondOnlinePlayer extends Condition {
	
	//[skellett[ ][cord]] %string% is online [bungee[ ][cord]]
	
	private Expression<String> player;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<String>) e[0];
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett[ ][cord]] %string% is online [bungee[ ][cord]]";
	}
	public boolean check(Event e) {
		PacketIsPlayerOnline packet = new PacketIsPlayerOnline(player.getSingle(e));
		Object obj = packet.send();
		IsOnlineResult isOnline = (IsOnlineResult) obj;
		if (isOnline == IsOnlineResult.ONLINE) {
			return true;
		} else {
			return false;
		}
	}
}