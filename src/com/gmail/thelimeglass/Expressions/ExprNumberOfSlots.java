package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprNumberOfSlots extends SimpleExpression<Integer>{
	
	//[skellett] (gui|menu|inventory|chest|window) (size|number|slots) (of|from) %inventory%
	
	private Expression<Inventory> inventory;
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
		inventory = (Expression<Inventory>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] (gui|menu|inventory|chest|window) (size|number|slots) (of|from) %inventory%";
	}
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		return new Integer[]{(inventory.getSingle(e)).getSize()};
	}
}