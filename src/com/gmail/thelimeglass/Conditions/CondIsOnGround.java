package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondIsOnGround extends Condition {
	
	//[(is|are)] [on] [the] ground [state] [of] [entity] %entity%
	//[entity] %entity% [(is|are)] [on] [the] ground [state]
	
	private Expression<Entity> entity;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<Entity>) e[0];
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[entity] %entity% [(is|are)] [on] [the] ground [state]";
	}
	public boolean check(Event e) {
		try {
			if (((Entity)entity).isOnGround()) {
				return true;
			} else {
				return false;					
			}
		}
		catch (Exception e1) {
			return false;
		}
	}
}