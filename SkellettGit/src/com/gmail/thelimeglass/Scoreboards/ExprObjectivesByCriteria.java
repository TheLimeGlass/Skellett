package com.gmail.thelimeglass.Scoreboards;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprObjectivesByCriteria extends SimpleExpression<Objective> {
	
	//[(the|all)] [of] [the] (score[ ][board]|board)[[']s] objectives (by|with) [criteria] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]
	
	private Expression<String> criteria;
	private Expression<Scoreboard> scoreboard;
	@Override
	public boolean isSingle() {
		return false;
	}
	@Override
	public Class<? extends Objective> getReturnType() {
		return Objective.class;
	}
	@Override
	public String toString(Event event, boolean b) {
		return "[(the|all)] [of] [the] (score[ ][board]|board)[[']s] objectives (by|with) [criteria] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]";
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		criteria = (Expression<String>) e[0];
		scoreboard = (Expression<Scoreboard>) e[1];
		return true;
	}
	@Override
	protected Objective[] get(final Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		if (scoreboard != null) {
			board = scoreboard.getSingle(e);
		}
		ArrayList<Objective> objectives = new ArrayList<>();
		for (Objective obj : board.getObjectivesByCriteria(criteria.getSingle(e))) {
			objectives.add(obj);
		}
		return objectives.toArray(new Objective[objectives.size()]);
	}
}