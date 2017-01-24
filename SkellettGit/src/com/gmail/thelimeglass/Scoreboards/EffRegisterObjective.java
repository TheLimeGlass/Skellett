package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffRegisterObjective extends Effect {
	
	//register [new] (score[ ][board]|[skellett[ ]]board) objective %string% with [criteria] %string% [(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]
	//register [new] objective %string% with [criteria] %string% [(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]
	
	private Expression<String> obj;
	private Expression<String> criteria;
	private Expression<Scoreboard> scoreboard;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		obj = (Expression<String>) e[0];
		criteria = (Expression<String>) e[1];
		scoreboard = (Expression<Scoreboard>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "register [new] (score[ ][board]|[skellett[ ]]board) objective %string% with [criteria] %string% [[(in|from)] %-scoreboard%]";
	}
	@Override
	protected void execute(Event e) {
		if (scoreboard != null) {
			scoreboard.getSingle(e).registerNewObjective(obj.getSingle(e), criteria.getSingle(e));
		} else {
			Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
			board.registerNewObjective(obj.getSingle(e), criteria.getSingle(e));
		}
	}
}