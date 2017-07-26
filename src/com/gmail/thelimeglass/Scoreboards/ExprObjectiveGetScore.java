package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprObjectiveGetScore extends SimpleExpression<Score>{
	
	//(score[ ][board]|[skellett[ ]]board) %objective%['s] [objective['s]] score (for|of) [entry] %string%
	//(score[ ][board]|[skellett[ ]]board) [objective] %objective%['s] score (for|of) [entry] %string%
	
	private Expression<Objective> obj;
	private Expression<String> entry;
	@Override
	public Class<? extends Score> getReturnType() {
		return Score.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		obj = (Expression<Objective>) e[0];
		entry = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "(score[ ][board]|[skellett[ ]]board) [objective] %objective% score (for|of) [entry] %string%";
	}
	@Override
	@Nullable
	protected Score[] get(Event e) {
		return new Score[]{obj.getSingle(e).getScore(entry.getSingle(e))};
	}
}