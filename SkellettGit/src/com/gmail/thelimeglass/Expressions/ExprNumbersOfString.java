package com.gmail.thelimeglass.Expressions;

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

@Syntax("[get] [the] (digit|num[ber])[s] (of|from|in) %string%")
@Config("NumbersInString")
@PropertyType(ExpressionType.COMBINED)
public class ExprNumbersOfString extends SimpleExpression<String>{
	
	private Expression<String> string;
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
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		string = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[get] [the] (digit|num[ber])[s] (of|from|in) %string%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{string.getSingle(e).replaceAll("[^0-9]", "")};
	}
}