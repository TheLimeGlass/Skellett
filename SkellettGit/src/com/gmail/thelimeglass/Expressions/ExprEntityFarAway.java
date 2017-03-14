package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax("[skellett] [(despawn|remove|clear)] [get] %entity% when [it([']s| is)] far away [state]")
@Config("EntityRemoveWhenFarAway")
@PropertyType(ExpressionType.COMBINED)
public class ExprEntityFarAway extends SimpleExpression<Boolean>{
	
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
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entity = (Expression<LivingEntity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [(despawn|remove|clear)] [get] %entity% when [it([']s| is)] far away [state]";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		if (entity.getSingle(e) == null) {
			return null;
		}
		return new Boolean[]{entity.getSingle(e).getRemoveWhenFarAway()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			entity.getSingle(e).setRemoveWhenFarAway((Boolean)delta[0]);
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