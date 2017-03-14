package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[skellett] final damage")
@Config("FinalDamage")
@PropertyType(ExpressionType.SIMPLE)
public class ExprFinalDamage extends SimpleExpression<Double> {
	
	public Class<? extends Double> getReturnType() {
		return Double.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(EntityDamageEvent.class)) {
			Skript.error("You can not use Final Damage expression in any event but on damage event!");
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