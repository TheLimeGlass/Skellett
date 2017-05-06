package com.gmail.thelimeglass.Expressions;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
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

@Syntax("[skellett] entity (from [the]|of) (uuid|[universal] unique id) %string%")
@Config("EntityUUID")
@PropertyType(ExpressionType.COMBINED)
public class ExprEntityFromUUID extends SimpleExpression<Entity>{
	
	private Expression<String> uuid;
	@Override
	public Class<? extends Entity> getReturnType() {
		return Entity.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		uuid = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] entity (from [the]|of) (uuid|[universal] unique id) %string%";
	}
	@Override
	@Nullable
	protected Entity[] get(Event e) {
		UUID uniqueId = null;
		try {
			uniqueId = UUID.fromString(uuid.getSingle(e));
			if (uniqueId != null) {
				return new Entity[]{Bukkit.getEntity(uniqueId)};
			}
		} catch (IllegalArgumentException ex) {}
		return null;
	}
}