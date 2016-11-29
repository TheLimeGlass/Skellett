package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprGetEntryTeam extends SimpleExpression<Team>{
	
	//(score[ ][board]|board) team of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]
	//(score[ ][board]|board) [entry] %string%'s team [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]
	
	private Expression<String> entry;
	private Expression<Scoreboard> scoreboard;
	@Override
	public Class<? extends Team> getReturnType() {
		return Team.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entry = (Expression<String>) e[0];
		scoreboard = (Expression<Scoreboard>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "(score[ ][board]|board) team of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]";
	}
	@Override
	@Nullable
	protected Team[] get(Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		if (scoreboard != null) {
			board = scoreboard.getSingle(e);
		}
		return new Team[]{board.getEntryTeam(entry.getSingle(e))};
	}
}