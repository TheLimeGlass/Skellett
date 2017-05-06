package com.gmail.thelimeglass.versionControl;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.ReflectionUtil;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] absorption hearts of %player%", "[skellett] %player%'s absorption hearts"})
@Config("AbsorptionHearts")
@PropertyType(ExpressionType.COMBINED)
public class ExprAbsorptionHearts extends SimpleExpression<Number>{
	
	private Expression<Player> player;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] absorption hearts of %player%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{getAbsorptionHearts(player.getSingle(e))};
	}
	@Override
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
	}

	private Number getAbsorptionHearts(Player player) {
		try {
			Object handle = ReflectionUtil.getHandle(player);
			Float aborption = (Float) handle.getClass().getMethod("getAbsorptionHearts").invoke(handle);
			return aborption;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void setAbsorptionHearts(Player player, Number number) {
		try {
			Object handle = ReflectionUtil.getHandle(player);
			handle.getClass().getMethod("setAbsorptionHearts", Float.TYPE).invoke(handle, number.floatValue());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
}