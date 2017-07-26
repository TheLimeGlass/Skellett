package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;

import com.gmail.thelimeglass.Skellett;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprNewScoreboard extends SimpleExpression<Scoreboard>{
	
	//[create] [a] new (score[ ][board]|[skellett[ ]]board)) [(with|named)] [(name|id)] %string%
	
	private Expression<String> name;
	@Override
	public Class<? extends Scoreboard> getReturnType() {
		return Scoreboard.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		name = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[create] [a] new (score[ ][board]|[skellett[ ]]board)) [(with|named)] [(name|id)] %string%";
	}
	@Override
	@Nullable
	protected Scoreboard[] get(Event e) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Skellett.skellettBoards.put(name.getSingle(e), board);
		return new Scoreboard[]{board};
	}
}