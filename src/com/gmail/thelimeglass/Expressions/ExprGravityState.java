package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.gmail.thelimeglass.Utils.Annotations.Version;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] gravity [state[s]] [(of|for)] [entit(y|ies)] %entities%", "[skellett] %entities%'s gravity [state[s]]"})
@Config("GravityState")
@PropertyType(ExpressionType.COMBINED)
@Version("1.10")
public class ExprGravityState extends SimpleExpression<Boolean>{
	
	private Expression<Entity> entities;
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entities = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "gravity [state[s]] [of] [entit(y|ies)] %entities%";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		List<Boolean> silentStates = new ArrayList<Boolean>();
		for (final Entity ent : entities.getAll(e)) {
			silentStates.add((Boolean)ent.hasGravity());
		}
		return silentStates.toArray(new Boolean[silentStates.size()]);
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET)
			for (final Entity ent : entities.getAll(e)) {
				ent.setGravity((Boolean)delta[0]);
			}
		if (mode == ChangeMode.RESET || mode == ChangeMode.REMOVE_ALL)
			for (final Entity ent : entities.getAll(e)) {
				ent.setGravity(false);
			}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.REMOVE_ALL) {
			return CollectionUtils.array(Boolean.class);
		}
		return null;
	}
}