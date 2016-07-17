package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Slime;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprSlimeSize extends SimpleExpression<Integer>{
	
	//[skellett] [the] slime size of %entity%
	
	private Expression<LivingEntity> entity;
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
		entity = (Expression<LivingEntity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [the] slime size of %entity%";
	}
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		if (entity instanceof Slime || entity instanceof MagmaCube) {
			return new Integer[]{((Slime)entity).getSize()};
		}
		return null;	
	}
}