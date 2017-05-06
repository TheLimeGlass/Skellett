package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprScoreObjective extends SimpleExpression<Objective>{
	
	//[the] (score[ ][board]|[skellett[ ]]board) objective (from|of) score %score%
	//[the] (score[ ][board]|[skellett[ ]]board) %score%'s scores objective
	
	private Expression<Score> score;
	@Override
	public Class<? extends Objective> getReturnType() {
		return Objective.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		score = (Expression<Score>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] (score[ ][board]|[skellett[ ]]board) objective (from|of) score %score%";
	}
	@Override
	@Nullable
	protected Objective[] get(Event e) {
		return new Objective[]{score.getSingle(e).getObjective()};
	}
}