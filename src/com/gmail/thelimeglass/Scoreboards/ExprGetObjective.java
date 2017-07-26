package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprGetObjective extends SimpleExpression<Objective>{
	
	//[the] (score[ ][board]|[skellett[ ]]board) objective %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]
	
	private Expression<Scoreboard> scoreboard;
	private Expression<String> obj;
	@Override
	public Class<? extends Objective> getReturnType() {
		return Objective.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (parser.mark >= 2) {
			scoreboard = (Expression<Scoreboard>) e[0];
			obj = (Expression<String>) e[1];
		} else {
			obj = (Expression<String>) e[0];
			scoreboard = (Expression<Scoreboard>) e[1];
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "objective %string% in (score[ ][board]|[skellett[ ]]board) [[(of|named|from)] %-scoreboard%]";
	}
	@Override
	@Nullable
	protected Objective[] get(Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		if (scoreboard != null) {
			board = scoreboard.getSingle(e);
		}
		if (obj.getSingle(e).equals("BELOW_NAME")||obj.getSingle(e).equals("PLAYER_LIST")||obj.getSingle(e).equals("SIDEBAR")) {
			DisplaySlot slot = DisplaySlot.valueOf(obj.getSingle(e).replace("\"", "").trim().replace(" ", "_").toUpperCase());
			try {
				slot = DisplaySlot.valueOf(slot.toString().replace("\"", "").trim().replace(" ", "_").toUpperCase());
			} catch (IllegalArgumentException t) {
				return null;
			}
			return new Objective[]{board.getObjective(slot)};
		} else {
			return new Objective[]{board.getObjective(obj.getSingle(e))};
		}
	}
}