package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityUnleashEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprUnleashHitch extends SimpleExpression<Entity> {
	
	//event-hitch
	
	public Class<? extends Entity> getReturnType() {
		return Entity.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (!ScriptLoader.isCurrentEvent(EntityUnleashEvent.class)) {
			Skript.error("You can not use UnleashHitch expression in any event but on entity unleash!");
			return false;
		}
		setTime(1);
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Unleash hitch";
	}
	@Nullable
	protected Entity[] get(Event e) {
		try {
			return new Entity[]{((LivingEntity) ((EntityUnleashEvent)e).getEntity()).getLeashHolder()};
		} catch (IllegalStateException error) {}
		return null;
	}
}