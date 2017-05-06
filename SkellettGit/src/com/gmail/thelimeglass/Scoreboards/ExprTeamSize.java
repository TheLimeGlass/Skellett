package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Team;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTeamSize extends SimpleExpression<Number>{
	
	//[(score[ ][board]|[skellett[ ]]board)] team size [(for|of)] [team] %team%
	
	private Expression<Team> team;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
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
		return "[(score[ ][board]|[skellett[ ]]board)] team size [(for|of)] [team] %team%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{team.getSingle(e).getSize()};
	}
}