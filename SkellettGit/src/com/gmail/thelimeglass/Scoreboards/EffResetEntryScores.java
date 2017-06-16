package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffResetEntryScores extends Effect {
	
	//reset [(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) scores of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]
	//reset [(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) [entry] %string%'s scores [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]
	//(score[ ][board]|[skellett[ ]]board) reset [(the|all)] [of] [the] scores of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]
	
	private Expression<String> entry;
	private Expression<Scoreboard> scoreboard;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entry = (Expression<String>) e[0];
		scoreboard = (Expression<Scoreboard>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "reset [(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) scores of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]";
	}
	@Override
	protected void execute(Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		if (scoreboard != null) {
			board = scoreboard.getSingle(e);
		}
		board.resetScores(entry.getSingle(e));
	}
}