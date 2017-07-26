package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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

@Syntax({"[skellett] custom name visib(le|ility) of %entity%", "[skellett] visib(le|ility) of %entity%'s custom name"})
@Config("CustomName")
@PropertyType(ExpressionType.COMBINED)
public class ExprCustomNameVisible extends SimpleExpression<Boolean>{
	
	private Expression<LivingEntity> entity;
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
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
		return "[skellett] custom name visib(le|ility) of %entity%";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		if (entity != null) {
			if (entity.getSingle(e) instanceof LivingEntity || entity.getSingle(e) instanceof Entity) {
				return new Boolean[]{entity.getSingle(e).isCustomNameVisible()};
			}
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (entity != null) {
				if (entity.getSingle(e) instanceof LivingEntity || entity.getSingle(e) instanceof Entity) {
					((LivingEntity)entity.getSingle(e)).setCustomNameVisible((Boolean)delta[0]);
				}
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Boolean.class);
		}
		return null;
	}
}