package com.gmail.thelimeglass.MySQL;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.event.Event;

public class EffMySQLDisconnect extends Effect {

	//[skellett] disconnect [from] mysql
	
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] disconnect [to] mysql";
	}
	@Override
	protected void execute(Event e) {
		MySQLManager.disconnect();
	}
}
