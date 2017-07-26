package com.gmail.thelimeglass.ReflectionSyntax;

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
import net.minecraft.server.v1_11_R1.DataWatcherObject;
import net.minecraft.server.v1_11_R1.DataWatcherRegistry;

@Syntax("data watcher value %number% from %entity%")
@Config("DataWatcher")
@PropertyType(ExpressionType.COMBINED)
public class ExprDataWatcher extends SimpleExpression<Object>{
	
	private Expression<Number> id;
	private Expression<Entity> entity;
	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		id = (Expression<Number>) e[0];
		entity = (Expression<Entity>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "data watcher value %number% from %entity%";
	}
	@Override
	@Nullable
	protected Object[] get(Event e) {
		if (entity == null) return null;
		try {
			Entity ent = entity.getSingle(e);
			net.minecraft.server.v1_11_R1.Entity craftEntity = (net.minecraft.server.v1_11_R1.Entity) ReflectionUtil.getHandle(ent);
			Object oreturn = craftEntity.getDataWatcher().get(new DataWatcherObject<>(id.getSingle(e).intValue(), DataWatcherRegistry.b));
			if (oreturn != null) {
				return new Object[]{oreturn};
			}
			return null;
		} catch (SecurityException | NoSuchMethodException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	/*@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number num = (Number)delta[0];
		Number numNow = getAbsorptionHearts(player.getSingle(e));
		if (mode == ChangeMode.SET) {
			setAbsorptionHearts(player.getSingle(e), num.floatValue());
		} else if (mode == ChangeMode.RESET) {
			setAbsorptionHearts(player.getSingle(e), 0);
		} else if (mode == ChangeMode.ADD) {
			setAbsorptionHearts(player.getSingle(e), numNow.floatValue() + num.floatValue());
		} else if (mode == ChangeMode.REMOVE) {
			setAbsorptionHearts(player.getSingle(e), numNow.floatValue() - num.floatValue());
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}*/
}