package com.gmail.thelimeglass.Nametags;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprGetNametagSuffix extends SimpleExpression<String>{
	
	//[skellett] [get] suffix [of] [the] [name][ ]tag [with] [id] %string%
	
	private Expression<String> nametag;
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
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		nametag = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [get] suffix [of] [the] [name][ ]tag [with] [id] %string%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{SkellettNametags.getNametagSuffix(nametag.getSingle(e))};
	}
}