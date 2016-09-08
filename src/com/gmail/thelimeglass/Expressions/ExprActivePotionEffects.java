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

public class ExprActivePotionEffects extends SimpleExpression<PotionEffect>{
	
	//[(the|all)] [of] [the] [active] potion[s] [effects] (on|of) %entity%
	
	private Expression<LivingEntity> entity;
	@Override
	public Class<? extends PotionEffect> getReturnType() {
		return PotionEffect.class;
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
	protected PotionEffect[] get(Event e) {
		ArrayList<PotionEffect> potions = new ArrayList<>();
		potions.addAll(entity.getSingle(e).getActivePotionEffects());
		return potions.toArray(new PotionEffect[potions.size()]);
	}
}