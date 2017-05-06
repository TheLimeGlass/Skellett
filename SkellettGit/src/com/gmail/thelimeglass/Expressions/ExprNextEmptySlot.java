package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("(next|first) empty slot of %inventory%")
@Config("NextEmptySlot")
@PropertyType(ExpressionType.COMBINED)
public class ExprNextEmptySlot extends SimpleExpression<Integer> {
	
	private Expression<Inventory> inventory;
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		inventory = (Expression<Inventory>) e[0];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "(next|first) empty slot of %inventory%";
	}
	@Nullable
	protected Integer[] get(Event e) {
		return new Integer[]{inventory.getSingle(e).firstEmpty()};
	}
}