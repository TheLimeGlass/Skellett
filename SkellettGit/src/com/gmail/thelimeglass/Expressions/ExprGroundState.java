package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[(is|are)] [on] [the] ground [state] [of] [entity] %entity%", "[entity] %entity% [(is|are)] [on] [the] ground [state]"})
@Config("GroundState")
@PropertyType(ExpressionType.COMBINED)
public class ExprGroundState extends SimpleExpression<Boolean>{
	
	private Expression<Entity> entity;
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
		entity = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(is|are)] [on] [the] ground [state] [of] [entity] %entity%";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		if (entity != null) {
			return new Boolean[]{entity.getSingle(e).isOnGround()};
		}
		return null;
	}
}