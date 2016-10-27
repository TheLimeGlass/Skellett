package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Skellett;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprCustomName extends SimpleExpression<String>{
	
	//[skellett] [get] custom name of %entity%
	
	private Expression<LivingEntity> entity;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
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
		return "[skellett] custom name of %entity%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{entity.getSingle(e).getCustomName()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			((LivingEntity)entity.getSingle(e)).setCustomName(Skellett.cc((String)delta[0]));
			((LivingEntity)entity.getSingle(e)).setCustomNameVisible(true);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(String.class);
		return null;
	}
}