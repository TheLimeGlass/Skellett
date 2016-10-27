package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprEntityID extends SimpleExpression<Number>{
	
	//[the] [entity] [number] id (of|from) %entity%
	//%entity%'s [entity] [number] id
	
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
		return "[the] [entity] [number] id (of|from) %entity%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{entity.getSingle(e).getEntityId()};
	}
}