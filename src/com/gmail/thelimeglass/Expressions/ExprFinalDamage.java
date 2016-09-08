package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprFinalDamage extends SimpleExpression<Double> {
	
	//[skellett] final damage
	
	public Class<? extends Double> getReturnType() {
		return Double.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent((Class)EntityDamageEvent.class)) {
			Skript.error((String)"You can not use Final Damage expression in any event but on damage event!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Final damage";
	}
	@Nullable
	protected Double[] get(Event e) {
		return new Double[]{((EntityDamageEvent)e).getFinalDamage()};
	}
}