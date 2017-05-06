package com.gmail.thelimeglass.Expressions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[skellett] ping of [player] %player%", "[skellett] %player%'s ping"})
@Config("PlayerPing")
@PropertyType(ExpressionType.COMBINED)
public class ExprPlayerPing extends SimpleExpression<Number> {
	
	private Expression<Player> player;
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parse) {
		player = (Expression<Player>) e[0];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[skellett] ping of [player] %player%";
	}
	@Nullable
	protected Number[] get(Event e) {
		if (player != null) {
			try {
				Method method = player.getSingle(e).getClass().getMethod("getHandle");
				method.setAccessible(true);
				Object nmsPlayer = method.invoke(player.getSingle(e));
				Field field = nmsPlayer.getClass().getField("ping");
				field.setAccessible(true);
				return new Number[]{field.getInt(nmsPlayer)};
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException | SecurityException | NoSuchFieldException | InvocationTargetException e1) {
				e1.printStackTrace();
				return new Number[]{1};
			}
		}
		return null;
	}
}