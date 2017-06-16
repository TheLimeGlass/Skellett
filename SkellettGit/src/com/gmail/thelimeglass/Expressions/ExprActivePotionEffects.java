package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(the|all)] [of] [the] [active] potion[s] [effects] (on|of) %entity%")
@Config("ActivePotionEffects")
@PropertyType(ExpressionType.COMBINED)
public class ExprActivePotionEffects extends SimpleExpression<String>{
	
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
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
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