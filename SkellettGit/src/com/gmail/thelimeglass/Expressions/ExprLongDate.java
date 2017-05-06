package com.gmail.thelimeglass.Expressions;

import java.util.Date;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("date (from|of) [(long|milliseconds)] %number%")
@Config("LongDate")
@PropertyType(ExpressionType.COMBINED)
public class ExprLongDate extends SimpleExpression<String>{
	
	private Expression<Number> milliseconds;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		milliseconds = (Expression<Number>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "date (from|of) [(long|milliseconds)] %number%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		if (milliseconds != null) {
			return new String[]{new Date(milliseconds.getSingle(e).longValue()).toString()};
		}
		return null;
	}
}