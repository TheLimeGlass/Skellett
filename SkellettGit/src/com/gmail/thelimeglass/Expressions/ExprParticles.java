package com.gmail.thelimeglass.Expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.Effect;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("[(the|all)] [of] [the] particle[[ ]types]")
@Config("GetParticles")
@PropertyType(ExpressionType.SIMPLE)
public class ExprParticles extends SimpleExpression<Effect> {
	
	@Override
	public boolean isSingle() {
		return false;
	}
	@Override
	public Class<? extends Effect> getReturnType() {
		return Effect.class;
	}
	@Override
	public String toString(Event event, boolean b) {
		return "[(the|all)] [of] [the] particle[[ ]types]";
	}
	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		return true;
	}
	@Override
	protected Effect[] get(Event event) {
		return Effect.values();
	}
}