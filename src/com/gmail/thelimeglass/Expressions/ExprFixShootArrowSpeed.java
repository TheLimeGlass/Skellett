package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprFixShootArrowSpeed extends SimpleExpression<Float> {
	
	//(arrow|shot|velocity) speed [of (shot|arrow)]
	
	public Class<? extends Float> getReturnType() {
		return Float.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent((Class)EntityShootBowEvent.class)) {
			Skript.error((String)"You can not use Arrow speed expression in any event but 'on entity shoot:' event!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Arrow speed";
	}
	@Nullable
	protected Float[] get(Event e) {
		return new Float[]{((EntityShootBowEvent)e).getForce()};
	}
}