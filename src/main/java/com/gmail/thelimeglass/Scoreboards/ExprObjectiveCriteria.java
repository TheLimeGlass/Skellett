package com.gmail.thelimeglass.Scoreboards;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprObjectiveCriteria extends SimpleExpression<String>{
	
	//(score[ ][board]|[skellett[ ]]board) objective criteria [of] %objective%
	//(score[ ][board]|[skellett[ ]]board) %objective%'s objective criteria
	
	private Expression<Objective> obj;
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
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		obj = (Expression<Objective>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "(score[ ][board]|[skellett[ ]]board) objective criteria [of] %objective%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{obj.getSingle(e).getCriteria()};
	}
}