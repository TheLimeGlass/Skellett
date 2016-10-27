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
	
	//[skellett] reset [(the|all)] [of] [the] (score[ ][board]|board) scores of [entry] %string%
	//[skellett] reset [(the|all)] [of] [the] (score[ ][board]|board) [entry] %string%'s scores
	//[skellett] (score[ ][board]|board) reset [(the|all)] [of] [the] scores of [entry] %string%
	
	private Expression<String> entry;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entry = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] reset [(the|all)] [of] [the] (score[ ][board]|board) scores of [entry] %string%";
	}
	@Override
	protected void execute(Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		board.resetScores(entry.getSingle(e));
	}
}