package com.gmail.thelimeglass.Scoreboards;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTeams extends SimpleExpression<Team> {
	
	//[(the|all)] [of] [the] teams [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]
	
	private Expression<Scoreboard> scoreboard;
	@Override
	public boolean isSingle() {
		return false;
	}
	@Override
	public Class<? extends Team> getReturnType() {
		return Team.class;
	}
	@Override
	public String toString(Event event, boolean b) {
		return "[(the|all)] [of] [the] [[(in|from)] (score[ ][board]|[skellett[ ]]board)[[']s] teams [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]";
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		scoreboard = (Expression<Scoreboard>) e[0];
		return true;
	}
	@Override
	protected Team[] get(final Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		if (scoreboard != null) {
			board = scoreboard.getSingle(e);
		}
		ArrayList<Team> teams = new ArrayList<>();
		for (Team t : board.getTeams()) {
			teams.add(t);
		}
		return teams.toArray(new Team[teams.size()]);
	}
}