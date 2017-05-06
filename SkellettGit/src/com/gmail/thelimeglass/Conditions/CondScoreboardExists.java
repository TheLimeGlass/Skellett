package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("score[ ][board] %string% (1¦(is set|[does] exist[s])|2¦(is(n't| not) set|does(n't| not) exist[s]))")
@Config("ScoreboardExists")
public class CondScoreboardExists extends Condition {
	
	private Expression<String> scoreboard;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		scoreboard = (Expression<String>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "score[ ][board] %string% (1¦(is set|[does] exist[s])|2¦(is(n't| not) set|does(n't| not) exist[s]))";
	}
	public boolean check(Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(scoreboard.getSingle(e));
		if (team != null) {
			return isNegated();
		} else {
			return !isNegated();
		}
	}
}