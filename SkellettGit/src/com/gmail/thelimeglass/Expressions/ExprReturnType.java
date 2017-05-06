package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(|skript|skellett)] [the] return (type[s]|value[s]) [(from|of|in)] [the] [expression] %object%")
@Config("Main.ReturnType")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprReturnType extends SimpleExpression<Class<?>>{
	
	private Expression<?> expression;
	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends Class<?>> getReturnType() {
		return (Class<? extends Class<?>>) Class.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		expression = e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(|skript|skellett)] return (type[s]|value[s]) [(from|of|in)] [the] [expression] %object%";
	}
	@Override
	protected Class<?>[] get(Event e) {
		 return new Class<?>[]{expression.getReturnType()};
	}
}