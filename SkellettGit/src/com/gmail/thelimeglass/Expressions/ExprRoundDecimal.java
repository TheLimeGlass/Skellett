package com.gmail.thelimeglass.Expressions;

import ch.njol.skript.classes.Changer;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("[Skellett] %number% round[ed] [to] [the] [nearest] %number% decimal (digit[s]|place[s])")
@Config("RoundDecimal")
@PropertyType(ExpressionType.COMBINED)
public class ExprRoundDecimal extends SimpleExpression<Number> {
	
	private Expression<Number> input;
	private Expression<Number> output;
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
		input = (Expression<Number>) e[0];
		output = (Expression<Number>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[Skellett] %number% round[ed] [to] %number% decimal (digit[s]|place[s]";
	}
	public Class<?>[] acceptChange(Changer.ChangeMode changemode) {
		return null;
	}
	protected Number[] get(Event e) {
		Number input = (Number)this.input.getSingle(e);
		Number output = (Number)this.output.getSingle(e);
		if (input == null | output == null) {
			return null;
		}
		return new Number[]{Math.round(input.doubleValue() * Math.pow(10.0, output.intValue())) / Math.pow(10.0, output.intValue())};
	}
}