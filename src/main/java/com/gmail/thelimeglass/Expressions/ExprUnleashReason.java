package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityUnleashEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprUnleashReason extends SimpleExpression<EntityUnleashEvent.UnleashReason> {
	
	//(un(leash|lead)|(leash|lead) break) reason
	
	public Class<? extends EntityUnleashEvent.UnleashReason> getReturnType() {
		return EntityUnleashEvent.UnleashReason.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (!ScriptLoader.isCurrentEvent(EntityUnleashEvent.class)) {
			Skript.error("You can not use UnleashReason expression in any event but on entity unleash!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Unleash reason";
	}
	@Nullable
	protected EntityUnleashEvent.UnleashReason[] get(Event e) {
		return new EntityUnleashEvent.UnleashReason[]{((EntityUnleashEvent)e).getReason()};
	}
}