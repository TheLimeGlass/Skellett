package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;

public class ExprAmountOfVariables extends SimpleExpression<Number> {

	//(size|amount) of [all] variables
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2,
			ParseResult arg3) {
		return true;
	}
	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "(size|amount) of [all] variables";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[] { Variables.numVariables() };
	}
}