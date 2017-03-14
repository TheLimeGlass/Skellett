package com.gmail.thelimeglass.Effects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.ReflectionUtil;
import com.gmail.thelimeglass.Utils.Syntax;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("skellett nms of %object%")
public class EffNMSTest extends Effect {
	
	private Expression<Object> object;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		object = (Expression<Object>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "skellett nms of %object%";
	}
	@Override
	protected void execute(Event e) {
		try {
			Object nmsObject = ReflectionUtil.getHandle(object.getSingle(e));
			//for (Field f : nmsObject.getClass().getFields()) {
			//	Bukkit.getLogger().info(f.getName() + " = " + f.get(nmsObject));
			//}
			for (Method f : nmsObject.getClass().getMethods()) {
				Bukkit.getLogger().info(f.getName());
			}
		} catch (SecurityException | NoSuchMethodException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}
}