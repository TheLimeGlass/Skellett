package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondClientTimeRelative extends Condition {
	
	//[skellett] [client] relative time of %player% [is] [%-boolean%] [relative] [to] [server]
	
	private Expression<Player> player;
	private Expression<Boolean> boo;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		if (e[1] != null) {
			boo = (Expression<Boolean>) e[1];
		}
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [client] relative time of %player% [is] [%-boolean%] [relative] [to] [server]";
	}
	public boolean check(Event e) {
		if (boo != null) {
			return player.getSingle(e).isPlayerTimeRelative();
		} else {
			try {
				player.getSingle(e).isPlayerTimeRelative();
				return true;
			}
			catch (Exception e1) {
				return false;
			}
		}
	}
}