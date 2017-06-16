package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] (fuse time|time until blowup) of [the] [primed] [tnt] %entity%", "[skellett] [primed] [tnt] %entity%['s] (fuse time|time until blowup)"})
@Config("TNTFuseTime")
@PropertyType(ExpressionType.COMBINED)
public class ExprFuseTime extends SimpleExpression<Number>{
	
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
		return "[skellett] (fuse time|time until blowup) of [the] [primed] [tnt] %entity%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (entity.getSingle(e) instanceof TNTPrimed) {
			return new Number[]{((TNTPrimed)entity.getSingle(e)).getFuseTicks()};
		} else {
			return null;
		}
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (entity.getSingle(e) instanceof TNTPrimed) {
			Number ticks = (Number)delta[0];
			Number ticksNow = ((TNTPrimed)entity.getSingle(e)).getFuseTicks();
			if (mode == ChangeMode.SET) {
				((TNTPrimed)entity.getSingle(e)).setFuseTicks(ticks.intValue());
			} else if (mode == ChangeMode.ADD) {
				((TNTPrimed)entity.getSingle(e)).setFuseTicks(ticksNow.intValue() + ticks.intValue());
			} else if (mode == ChangeMode.REMOVE) {
				((TNTPrimed)entity.getSingle(e)).setFuseTicks(ticksNow.intValue() - ticks.intValue());
			}
		} else {
			return;
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.REMOVE || mode == ChangeMode.ADD) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}