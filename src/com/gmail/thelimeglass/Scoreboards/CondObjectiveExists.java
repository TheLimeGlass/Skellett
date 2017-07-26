package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondObjectiveExists extends Condition {

	//objective %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]] (1¦(is set|[does] exist[s])|2¦(is(n't| not) set|does(n't| not) exist[s]))
	
	private Expression<String> obj;
	private Expression<Scoreboard> scoreboard;
	Boolean boo = true;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		obj = (Expression<String>) e[0];
		scoreboard = (Expression<Scoreboard>) e[1];
		if (parser.mark == 2) {boo = false;}
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "objective %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]] (1¦(is set|[does] exist[s])|2¦(is(n't| not) set|does(n't| not) exist[s])))";
	}
	public boolean check(Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		if (scoreboard != null) {
			board = scoreboard.getSingle(e);
		}
		if (board.getObjective(obj.getSingle(e)) != null) {
			if (boo == true) {
				return true;
			} else {
				return false;
			}
		} else {
			if (boo == false) {
				return true;
			} else {
				return false;
			}
		}
	}
}