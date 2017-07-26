package com.gmail.thelimeglass.Scoreboards;

import java.util.ArrayList;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Team;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTeamEntries extends SimpleExpression<String> {
	
	//[(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board)[[']s] entr(ies|y[[']s]) (in|within) [the] [team] %team%
	
	private Expression<Team> team;
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
		return "[(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board)[[']s] entr(ies|y[[']s])";
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		team = (Expression<Team>) e[0];
		return true;
	}
	@Override
	protected String[] get(final Event e) {
		ArrayList<String> entries = new ArrayList<>();
		for (String entry : team.getSingle(e).getEntries()) {
			entries.add(entry);
		}
		return entries.toArray(new String[entries.size()]);
	}
}