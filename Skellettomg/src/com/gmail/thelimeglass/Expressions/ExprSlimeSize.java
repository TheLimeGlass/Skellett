package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprSlimeSize extends SimpleExpression<Number>{
	
	//[skellett] slime size of %entity%
	//[skellett] %entity%'s slime size
	
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
		return "[skellett] slime size of %entity%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (entity.getSingle(e) == null) {
			return null;
		}
		if (entity.getSingle(e).toString().equals("CraftSlime") || entity.getSingle(e).toString().equals("CraftMagmaCube")) {
			return new Number[]{((Slime)entity.getSingle(e)).getSize()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (entity.getSingle(e) == null) {
			return;
		}
		if (entity.getSingle(e).toString().equals("CraftSlime") || entity.getSingle(e).toString().equals("CraftMagmaCube")) {
			if (mode == ChangeMode.SET) {
				Number data = (Number)delta[0];
				((Slime)entity.getSingle(e)).setSize(data.intValue());
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}