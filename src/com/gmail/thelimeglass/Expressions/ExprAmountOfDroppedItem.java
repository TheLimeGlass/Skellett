package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprAmountOfDroppedItem extends SimpleExpression<Number>{
	
	//[skellett] [get] (size|number|amount) of dropped %entity%
	
	private Expression<Entity> entity;
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
		entity = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [get] (size|number|amount) of dropped %entity%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (entity.getSingle(e).getType() == EntityType.DROPPED_ITEM) {
			return new Number[]{((Item)entity.getSingle(e)).getItemStack().getAmount()};
		} else {
			return new Number[]{0};
		}
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			((Item)entity.getSingle(e)).getItemStack().setAmount((Integer)delta[0]);
		} else if (mode == ChangeMode.RESET) {
			((Item)entity.getSingle(e)).getItemStack().setAmount(1);
		} else if (mode == ChangeMode.ADD) {
			((Item)entity.getSingle(e)).getItemStack().setAmount(((Item)entity.getSingle(e)).getItemStack().getAmount() + (Integer)delta[0]);
		} else if (mode == ChangeMode.REMOVE) {
			((Item)entity.getSingle(e)).getItemStack().setAmount(((Item)entity.getSingle(e)).getItemStack().getAmount() - (Integer)delta[0]);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(Number.class);
		return null;
	}
}