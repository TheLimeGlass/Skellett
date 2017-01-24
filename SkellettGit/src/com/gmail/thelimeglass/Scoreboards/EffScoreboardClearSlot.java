package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffScoreboardClearSlot extends Effect {
	
	//clear (score[ ][board]|board) [display] slot %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]
	
	private Expression<String> obj;
	private Expression<Scoreboard> scoreboard;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		obj = (Expression<String>) e[0];
		scoreboard = (Expression<Scoreboard>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "clear (score[ ][board]|board) [display] slot %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]";
	}
	@Override
	protected void execute(Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		if (scoreboard != null) {
			board = scoreboard.getSingle(e);
		}
		if (obj.getSingle(e).equals("BELOW_NAME")||obj.getSingle(e).equals("PLAYER_LIST")||obj.getSingle(e).equals("SIDEBAR")) {
			DisplaySlot slot = DisplaySlot.valueOf(obj.getSingle(e).replace("\"", "").trim().replace(" ", "_").toUpperCase());
			try {
				slot = DisplaySlot.valueOf(slot.toString().replace("\"", "").trim().replace(" ", "_").toUpperCase());
			} catch (IllegalArgumentException t) {
				return;
			}
			board.clearSlot(slot);
		} else {
			return;
		}
	}
}