package com.gmail.thelimeglass.Expressions;

import java.util.TreeMap;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.StringMode;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;

@Syntax("[(the|all)] [of] [the] indexes (of|[with]in) %~objects%")
@Config("Indexes")
@PropertyType(ExpressionType.COMBINED)
public class ExprIndexes extends SimpleExpression<String> {
	
	private Variable<?> variable;
	private VariableString variableString;
	
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (e[0] instanceof Variable && ((Variable<?>)e[0]).isList()) {
			variable = (Variable<?>) e[0];
			String var =  variable.isLocal() ? variable.toString().substring(2, variable.toString().length() - 1) : variable.toString().substring(1, variable.toString().length() - 1);
			variableString = VariableString.newInstance(var, StringMode.VARIABLE_NAME);
			return true;
		}
		Skript.error("Indexes within `%objects%` must be a list variable!");
		return false;
	}
	
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[(the|all)] [of] [the] indexes (of|[with]in) %~objects%";
	}
	
	@Nullable
	protected String[] get(Event e) {
		if (Variables.getVariable(variableString.toString(e), e, variable.isLocal()) == null) return null;
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> listVariable = (TreeMap<String, Object>) Variables.getVariable(variableString.toString(e), e, variable.isLocal());
		return (listVariable.isEmpty()) ? null : listVariable.keySet().toArray(new String[listVariable.keySet().size()]);
	}
}