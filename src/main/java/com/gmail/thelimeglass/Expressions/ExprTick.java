package com.gmail.thelimeglass.Expressions;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[the] server tick[s]", "[the] server's tick[s]", "[the] tick[s] of [the] server"})
@Config("ServerTick")
@PropertyType(ExpressionType.SIMPLE)
public class ExprTick extends SimpleExpression<Number>{
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] server tick[s]";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		try {
			Server server = Bukkit.getServer();
			Field consoleField = server.getClass().getDeclaredField("console");
			consoleField.setAccessible(true);
			Object nmsServer = consoleField.get(server);
			Field tps = nmsServer.getClass().getSuperclass().getDeclaredField("ticks");
			tps.setAccessible(true);
			return new Number[]{tps.getInt(nmsServer)};
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}