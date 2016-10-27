package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprNearbyEntities extends SimpleExpression<Entity>{
	
	//[skellett] [all] [nearby] entit(y|ies) (within|in) [a] radius [of] %number%[(,| and) %-number%(,| and) %-number%] (within|around|near) %location%
	
	private Expression<Number> radius;
	private Expression<Number> radiusExtra1;
	private Expression<Number> radiusExtra2;
	private Expression<Location> location;
	@Override
	public Class<? extends Entity> getReturnType() {
		return Entity.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		radius = (Expression<Number>) e[0];
		radiusExtra1 = (Expression<Number>) e[1];
		radiusExtra2 = (Expression<Number>) e[2];
		location = (Expression<Location>) e[3];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [all] [nearby] entit(y|ies) (within|in) [a] radius [of] %number%[(,| and) %number%(,| and) %number%] (within|around|near) %location%";
	}
	@Override
	@Nullable
	protected Entity[] get(Event e) {
		ArrayList<Entity> entities = new ArrayList<>();
		if (radiusExtra1 != null || radiusExtra2 != null) {
			for (Entity ent : location.getSingle(e).getWorld().getNearbyEntities(location.getSingle(e), radius.getSingle(e).doubleValue(), radiusExtra1.getSingle(e).doubleValue(), radiusExtra2.getSingle(e).doubleValue())) {
				entities.add(ent);
			}
		} else {
			for (Entity ent : location.getSingle(e).getWorld().getNearbyEntities(location.getSingle(e), radius.getSingle(e).doubleValue(), radius.getSingle(e).doubleValue(), radius.getSingle(e).doubleValue())) {
				entities.add(ent);
			}
		}
		return entities.toArray(new Entity[entities.size()]);
	}
}