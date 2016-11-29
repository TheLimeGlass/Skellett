package com.gmail.thelimeglass.Feudal;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import us.forseth11.feudal.core.Feudal;

public class ExprFeudalMessage extends SimpleExpression<String>{
	
	//Feudal config message %string%
	
	Feudal feudal = Feudal.getPlugin();
	private Expression<String> node;
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
		node = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "Feudal config message %string%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{Feudal.getMessage(node.getSingle(e))};
	}
}