package me.limeglass.skellett.elements.expressions;

import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

public class ExprAbsoluteValue extends SimplePropertyExpression<Double, Number> {

	static {
		register(ExprAbsoluteValue.class, Number.class, "absolute [value]", "numbers");
	}

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	@Nullable
	public Number convert(Double number) {
		return Math.abs(number);
	}

	@Override
	protected String getPropertyName() {
		return "absolute value";
	}

}
