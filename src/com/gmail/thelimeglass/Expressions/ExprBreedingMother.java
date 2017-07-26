package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityBreedEvent;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprBreedingMother extends SimpleExpression<LivingEntity>{
	
	//bre[e]d[ing] mother
	
	@Override
	public Class<? extends LivingEntity> getReturnType() {
		return LivingEntity.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(EntityBreedEvent.class)) {
			Skript.error("You can not use Breeder mother expression in any event but on breeding event!");
			return false;
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "bre[e]d[ing] mother";
	}
	@Override
	@Nullable
	protected LivingEntity[] get(Event e) {
		return new LivingEntity[]{((EntityBreedEvent)e).getMother()};
	}
}