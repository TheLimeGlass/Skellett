package com.gmail.thelimeglass.Scoreboards;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprGetEntryScores extends SimpleExpression<Score>{
	
	//[skellett] [(the|all)] [of] [the] (score[ ][board]|board) scores of [entry] %string%
	//[skellett] [(the|all)] [of] [the] (score[ ][board]|board) [entry] %string%'s scores
	
	private Expression<String> entry;
	@Override
	public Class<? extends Score> getReturnType() {
		return Score.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entry = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [(the|all)] [of] [the] (score[ ][board]|board) scores of [entry] %string%";
	}
	@Override
	@Nullable
	protected Score[] get(Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		ArrayList<Score> scores = new ArrayList<>();
		for (Score s : board.getScores(entry.getSingle(e))) {
			scores.add(s);
		}
		return scores.toArray(new Score[scores.size()]);
	}
}