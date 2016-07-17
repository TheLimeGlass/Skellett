package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondScoreboardExists extends Condition {
	
	//score[ ][board] %string% (is set|exists)
	
	private Expression<String> scoreboard;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		scoreboard = (Expression<String>) e[0];
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "score[ ][board] %string% (is set|exists)";
	}
	public boolean check(Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(scoreboard.getSingle(e));
		if (team != null) {
			return true;
		} else {
			return false;
		}
	}
}