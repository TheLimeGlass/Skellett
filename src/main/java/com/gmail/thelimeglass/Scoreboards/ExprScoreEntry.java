package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Score;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprScoreEntry extends SimpleExpression<String>{
	
	//(score[ ][board]|[skellett[ ]]board) [get] entry (from|of) score %score%
	//(score[ ][board]|[skellett[ ]]board) %score%'s score entry//
	
	private Expression<Score> score;
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
		score = (Expression<Score>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "(score[ ][board]|[skellett[ ]]board) [get] entry (from|of) score %score%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{score.getSingle(e).getEntry()};
	}
}