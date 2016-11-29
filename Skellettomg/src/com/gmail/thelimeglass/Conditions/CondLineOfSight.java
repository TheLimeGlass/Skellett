package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondLineOfSight extends Condition {
	
	//%entity% [can] (see|visibly see|line of sight) [can see] %entity%
	
	private Expression<LivingEntity> entity1;
	private Expression<LivingEntity> entity2;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity1 = (Expression<LivingEntity>) e[0];
		entity2 = (Expression<LivingEntity>) e[1];
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "%entity% [can] (see|visibly see|line of sight) [can see] %entity%";
	}
	public boolean check(Event e) {
        try {
        	((LivingEntity)entity1.getSingle(e)).hasLineOfSight(((LivingEntity)entity2.getSingle(e)));
            return true;
        }
        catch (Exception e1) {
            return false;
        }
    }
}