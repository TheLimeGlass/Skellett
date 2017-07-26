package com.gmail.thelimeglass.MySQL;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.event.Event;

public class EffMySQLUpdate extends Effect {

	//[skellett] mysql update %string%
	
	private Expression<String> string;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		string = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] mysql update %string%";
	}
	@Override
	protected void execute(Event e) {
		MySQLManager.update(string.getSingle(e));
	}
}
