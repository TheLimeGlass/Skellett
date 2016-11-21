package com.gmail.thelimeglass.Expressions;

import ch.njol.skript.lang.Expression;

import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.Effect;
import org.bukkit.event.Event;

import java.util.ArrayList;

public class ExprParticles extends SimpleExpression<Effect> {
	
	//[(the|all)] [of] [the] particle[[ ]types]
	
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
		ArrayList<Effect> particles = new ArrayList<>();
		for (Effect particle : Effect.values()) {
			particles.add(particle);
		}
		return particles.toArray(new Effect[particles.size()]);
	}
}