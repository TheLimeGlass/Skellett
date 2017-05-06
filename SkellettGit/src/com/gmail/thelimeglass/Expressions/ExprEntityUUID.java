package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[skellett] entity (uuid|[universal] unique id) of [entity] %entity%", "[entity] %entity%'s entity (uuid|[universal] unique id)"})
@Config("EntityUUID")
@PropertyType(ExpressionType.COMBINED)
public class ExprEntityUUID extends SimpleExpression<String>{
	
	private Expression<Entity> entity;
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
		entity = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] (uuid|[universal] unique id) of [entity] %entity%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		if (entity.getSingle(e).getType() != EntityType.PLAYER) {
			return new String[]{entity.getSingle(e).getUniqueId().toString()};
		} else {
			return new String[]{((Player)entity.getSingle(e)).getUniqueId().toString()};
		}
	}
}