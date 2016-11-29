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

public class ExprGetTeam extends SimpleExpression<Team>{
	
	//[skellett] (score[ ][board]|board) %string% team
	//[skellett] (score[ ][board]|board) [get] team %string%
	
	private Expression<String> team;
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
		team = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] (score[ ][board]|board) %string% team";
	}
	@Override
	@Nullable
	protected Team[] get(Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		if (board.getTeam(team.getSingle(e)) != null) {
			return new Team[]{board.getTeam(team.getSingle(e))};
		}
		return null;
	}
}