package com.gmail.thelimeglass.Conditions;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.ReflectionUtil;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[entity] %entity% (1¦is|2¦is(n't| not)) in water")
@Config("InWater")
public class CondIsInWater extends Condition {
	
	private Expression<Entity> entity;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entity = (Expression<Entity>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[entity] %entity% (1¦is|2¦is(n't| not)) in water";
	}
	public boolean check(Event e) {
		if (entity != null) {
			try {
				Object nmsEntity = ReflectionUtil.getHandle(entity.getSingle(e));
				if (nmsEntity != null) {
					return nmsEntity.getClass().getField("inWater").getBoolean(nmsEntity) ? isNegated() : !isNegated();
				}
			} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchMethodException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}
}