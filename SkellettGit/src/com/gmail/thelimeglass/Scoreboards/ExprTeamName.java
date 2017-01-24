package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Team;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTeamName extends SimpleExpression<String>{
	
	//[(score[ ][board]|[skellett[ ]]board)] [team] name [(for|of)] [team] %team%
	
	private Expression<Team> team;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		team = (Expression<Team>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(score[ ][board]|[skellett[ ]]board)] [team] name [(for|of)] [team] %team%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{team.getSingle(e).getName()};
	}
}