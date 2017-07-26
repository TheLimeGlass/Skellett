package com.gmail.thelimeglass.Scoreboards;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprEntries extends SimpleExpression<String> {
	
	//[(the|all)] [of] [the] (score[ ][board]|board)[[']s] entr(ies|y[[']s]) [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]
	
	private Expression<Scoreboard> scoreboard;
	@Override
	public boolean isSingle() {
		return false;
	}
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public String toString(Event event, boolean b) {
		return "[skellett] [(the|all)] [of] [the] (score[ ][board]|board)[[']s] entr(ies|y[[']s])";
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		scoreboard = (Expression<Scoreboard>) e[0];
		return true;
	}
	@Override
	protected String[] get(final Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		if (scoreboard != null) {
			board = scoreboard.getSingle(e);
		}
		ArrayList<String> entries = new ArrayList<>();
		for (String entry : board.getEntries()) {
			entries.add(entry);
		}
		return entries.toArray(new String[entries.size()]);
	}
}