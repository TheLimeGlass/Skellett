package com.gmail.thelimeglass.Holograms;

import javax.annotation.Nullable;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettAPI.SkellettHolograms;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprGetHologramName extends SimpleExpression<String>{
	
	//[get] (string|text|name) of holo[gram] [with] [id] %integer%
	
	private Expression<Integer> ID;
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
		ID = (Expression<Integer>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[get] (string|text|name) of holo[gram] [with] [id] %integer%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{SkellettHolograms.getHologramName(ID.getSingle(e))};
	}
}