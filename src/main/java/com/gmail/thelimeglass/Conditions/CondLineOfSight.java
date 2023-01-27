package com.gmail.thelimeglass.Conditions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("%entity% (1¦can|2¦can([ ]no|')t) [visibly] see %entity%")
@Config("LineOfSight")
public class CondLineOfSight extends Condition {
	
	private Expression<LivingEntity> viewer, entity;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		setNegated(parser.mark == 1);
		viewer = (Expression<LivingEntity>) e[0];
		entity = (Expression<LivingEntity>) e[1];
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "%entity% (1¦can|2¦can([ ]no|')t) [visibly] see %entity%";
	}
	public boolean check(Event e) {
		if (viewer.getSingle(e).hasLineOfSight(entity.getSingle(e))) {
			return isNegated();
		} else {
			return !isNegated();
		}
    }
}