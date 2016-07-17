package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprBlockGetPower extends SimpleExpression<Integer>{
	
	//[redstone] power [[being] receiv(ed|ing) [(from|at)]] %location%
	//%location% [redstone] power [[being] received]
	
	private Expression<Location> loc;
	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		loc = (Expression<Location>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[redstone] power [[being] receiv(ed|ing) [(from|at)]] %location%";
	}
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		return new Integer[]{loc.getSingle(e).getBlock().getBlockPower()};
	}
}