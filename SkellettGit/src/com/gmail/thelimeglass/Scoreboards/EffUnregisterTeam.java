package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Team;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffUnregisterTeam extends Effect {
	
	//unregister [the] (score[ ][board]|[skellett[ ]]board) team %team%
	
	private Expression<Team> team;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		team = (Expression<Team>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "unregister [the] (score[ ][board]|[skellett[ ]]board) team %team%";
	}
	@Override
	protected void execute(Event e) {
		team.getSingle(e).unregister();
	}
}