package com.gmail.thelimeglass.Expressions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[event] method %string% [is [a] loop[able] %-boolean%] [(from|of) [(expression|type|class)] %-object%] [with parameter[s] %-objects%]")
@Config("Main.ReturnType")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprCustomExpression extends SimpleExpression<Object>{
	
	private Expression<String> string;
	private Expression<Boolean> loop;
	private Expression<?> expression;
	private Expression<Object> parameter;
	private Boolean loopable = true;
	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}
	@Override
	public boolean isSingle() {
		return loopable;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		string = (Expression<String>) e[0];
		loop = (Expression<Boolean>) e[1];
		expression = e[2];
		parameter = (Expression<Object>) e[3];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[event] method %string% [is [a] loop[able] %-boolean%] [(from|of) [(expression|type|class)] %-object%] [with parameter[s] %-objects%]";
	}
	@Override
	@Nullable
	protected Object[] get(Event e) {
		if (loop != null) {
			loopable = loop.getSingle(e);
		}
		Method m = null;
		m = null;
		Object involker = e;
		Class<?> clazz = e.getClass();
		if (expression != null) {
			clazz = expression.getSingle(e).getClass();
			if (expression.getSingle(e) instanceof String) {
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
					return new Object[]{m.invoke(involker, parameter.getAll(e))};
				} else {
					return new Object[]{m.invoke(involker)};
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
}