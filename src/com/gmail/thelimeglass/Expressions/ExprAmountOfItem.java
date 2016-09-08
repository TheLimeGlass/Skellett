package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprAmountOfItem extends SimpleExpression<Number>{
	
	//[skellett] [get] (size|number|amount) of %itemstack%
	
	private Expression<ItemStack> item;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		item = (Expression<ItemStack>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [get] (size|number|amount) of %itemstack%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{item.getSingle(e).getAmount()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			item.getSingle(e).setAmount((Integer)delta[0]);
		} else if (mode == ChangeMode.RESET) {
			item.getSingle(e).setAmount(1);
		} else if (mode == ChangeMode.ADD) {
			item.getSingle(e).setAmount(item.getSingle(e).getAmount() + (Integer)delta[0]);
		} else if (mode == ChangeMode.REMOVE) {
			item.getSingle(e).setAmount(item.getSingle(e).getAmount() - (Integer)delta[0]);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(Integer.class);
		return null;
	}
}