package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprActivePotionEffects extends SimpleExpression<String>{
	
	//[(the|all)] [of] [the] [active] potion[s] [effects] (on|of) %entity%
	
	private Expression<LivingEntity> entity;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<LivingEntity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(the|all)] [of] [the] [active] potion[s] [effects] (on|of) %entity%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		ArrayList<String> potions = new ArrayList<>();
		for (final PotionEffect effect : entity.getSingle(e).getActivePotionEffects()) {
			if (effect.getAmplifier() != 0) {
				potions.add(effect.getType().getName() + " " + effect.getAmplifier());
			} else {
				potions.add(effect.getType().getName());
			}
		}
		return potions.toArray(new String[potions.size()]);
	}
}