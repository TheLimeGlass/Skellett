package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.Utils.Version;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax("glide [state] [(of|for)] [entit(y|ies)] %entity%")
@Config("GlideState")
@PropertyType(ExpressionType.COMBINED)
@Version("1.10")
public class ExprGlideState extends SimpleExpression<Boolean>{
	
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
		return "glide [state] [of] [entit(y|ies)] %entity%";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		if (entity.getSingle(e) == null) {
			return null;
		}
		return new Boolean[]{entity.getSingle(e).isGliding()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (entity.getSingle(e) == null) {
			return;
		}
		if (mode == ChangeMode.SET) {
			entity.getSingle(e).setGliding((Boolean)delta[0]);
		} else if (mode == ChangeMode.RESET) {
			entity.getSingle(e).setGliding(false);
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET) {
			return CollectionUtils.array(Boolean.class);
		}
		return null;
	}
}