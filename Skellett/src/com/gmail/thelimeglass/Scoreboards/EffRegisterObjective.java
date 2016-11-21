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
	
	//[skellett] register [new] (score[ ][board]|board) objective %string% with [criteria] %string%
	
	private Expression<String> obj;
	private Expression<String> criteria;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		obj = (Expression<String>) e[0];
		criteria = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] register [new] (score[ ][board]|board) objective %string% with [criteria] %string%";
	}
	@Override
	protected void execute(Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		if (board.getObjective(obj.getSingle(e)) != null) {
			return;
		}
		board.registerNewObjective(obj.getSingle(e), criteria.getSingle(e));
	}
}