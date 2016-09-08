package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.squidhq.plugin.APISingleton;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondSquidHQrunning extends Condition {
	
	//%player% is running [client] SquidHQ
	//%player%'s client is SquidHQ
	
	private Expression<Player> player;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "%player% is running [client] SquidHQ";
	}
	public boolean check(Event e) {
		try {
			if (APISingleton.getAPI().isRunning(player.getSingle(e))) {
				return true;
			} else {
				return false;					
			}
		}
		catch (Exception e1) {
			return false;
		}
	}
}