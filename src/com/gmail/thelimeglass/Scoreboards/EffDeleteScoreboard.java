package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Skellett;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffDeleteScoreboard extends Effect {
	
	//(delete|clear|remove) (score[ ][board]|[skellett[ ]]board)) [(with|named)] [(name|id)] %string%
	
	private Expression<String> name;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		name = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(delete|clear|remove) (score[ ][board]|[skellett[ ]]board)) [(with|named)] [(name|id)] %string%";
	}
	@Override
	protected void execute(Event e) {
		if (Skellett.skellettBoards.containsKey(name.getSingle(e))) {
			Skellett.skellettBoards.remove(name.getSingle(e));
		}
	}
}