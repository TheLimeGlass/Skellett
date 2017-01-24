package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[the] fall distance (from|of) %entity%", "%entity%'s fall distance"})
@Config("FallDistance")
@PropertyType("COMBINED")
public class ExprFallDistance extends SimpleExpression<Number>{
	
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
		return "[the] fall distance (from|of) %entity%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (entity != null) {
			return new Number[]{entity.getSingle(e).getFallDistance()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (entity != null) {
			Number distance = (Number)delta[0];
			Number distanceNow = entity.getSingle(e).getFallDistance();
			if (mode == ChangeMode.SET) {
				entity.getSingle(e).setFallDistance(distance.floatValue());
			} else if (mode == ChangeMode.ADD) {
				entity.getSingle(e).setFallDistance(distanceNow.floatValue() + distance.floatValue());
			} else if (mode == ChangeMode.REMOVE) {
				entity.getSingle(e).setFallDistance(distanceNow.floatValue() - distance.floatValue());
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.REMOVE || mode == ChangeMode.ADD) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}