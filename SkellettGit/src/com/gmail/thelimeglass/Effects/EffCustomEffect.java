package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("(invoke|execute|run) method %string% [(from|of) [(expression|type|class)] %*-object%] [with parameter[s] %-objects%]")
@Config("Main.ReturnType")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class EffCustomEffect extends Effect {
	
	private Expression<String> string;
	private Expression<?> expression;
	private Expression<Object> parameter;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		string = (Expression<String>) e[0];
		expression = e[1];
		parameter = (Expression<Object>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(invoke|execute|run) method %string% [(from|of) [(expression|type|class)] %-object%] [with parameter[s] %-objects%]";
	}
	@Override
	protected void execute(Event e) {
		Method m = null;
		m = null;
		Object involker = e;
		Class<?> clazz = e.getClass();
		if (expression != null) {
			clazz = expression.getSingle(e).getClass();
			if (clazz == String.class) {
				Class<?> testClass;
				try {
					testClass = Class.forName((String) expression.getSingle(e));
					if (testClass != null) {
						clazz = testClass;
					}
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			involker = expression.getSingle(e);
		}
		try {
			if (parameter != null) {
				m = clazz.getDeclaredMethod(string.getSingle(e), parameter.getAll(e).getClass());
			} else {
				m = clazz.getDeclaredMethod(string.getSingle(e));
			}
		} catch (NoSuchMethodException | SecurityException e1) {}
		if (m == null) {
			try {
				if (parameter != null) {
					m = clazz.getMethod(string.getSingle(e), parameter.getAll(e).getClass());
				} else {
					m = clazz.getMethod(string.getSingle(e));
				}
			} catch (NoSuchMethodException | SecurityException e1) {}
		}
		if (m == null && expression == null) {
			try {
				if (parameter != null) {
					m = clazz.getSuperclass().getDeclaredMethod(string.getSingle(e), parameter.getAll(e).getClass());
				} else {
					m = clazz.getSuperclass().getDeclaredMethod(string.getSingle(e));
				}
			} catch (NoSuchMethodException | SecurityException e1) {}
		}
		if (m != null) {
			try {
				if (parameter != null) {
					m.invoke(involker, parameter.getAll(e));
				} else {
					m.invoke(involker);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}
	}
}
