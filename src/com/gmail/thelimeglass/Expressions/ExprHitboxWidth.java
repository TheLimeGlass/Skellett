package com.gmail.thelimeglass.Expressions;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.ReflectionUtil;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[the] hitbox width of %entity%", "%entity%'s hitbox width", "[the] width of %entity%'s hitbox"})
@Config("HitboxSize")
@PropertyType(ExpressionType.COMBINED)
public class ExprHitboxWidth extends SimpleExpression<Number>{
	
	private Expression<Entity> entity;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entity = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] hitbox width of %entity%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (entity != null) {
			try {
				Object nmsEntity = ReflectionUtil.getHandle(entity.getSingle(e));
				if (nmsEntity != null) {
					Number length = (Number)nmsEntity.getClass().getField("width").get(nmsEntity);
					return new Number[]{length};
				}
			} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchMethodException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
}