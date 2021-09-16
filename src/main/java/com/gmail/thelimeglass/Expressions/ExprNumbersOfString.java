package com.gmail.thelimeglass.Expressions;

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