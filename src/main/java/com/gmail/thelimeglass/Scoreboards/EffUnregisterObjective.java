package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffUnregisterObjective extends Effect {
	
	//unregister (score[ ][board]|[skellett[ ]]board) objective %objective%
	
	private Expression<Objective> obj;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		obj = (Expression<Objective>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "unregister (score[ ][board]|[skellett[ ]]board) objective %objective%";
	}
	@Override
	protected void execute(Event e) {
		if (obj.getSingle(e) != null) {
			obj.getSingle(e).unregister();
		}
	}
}