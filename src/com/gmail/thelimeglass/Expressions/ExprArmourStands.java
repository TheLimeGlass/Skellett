package com.gmail.thelimeglass.Expressions;

import ch.njol.skript.lang.Expression;

import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import java.util.ArrayList;

public class ExprArmourStands extends SimpleExpression<LivingEntity> {
	
	//[(the|all)] [of] [the] armo[u]r stand[s]
	
	@Override
	public boolean isSingle() {
		return false;
	}
	@Override
	public Class<? extends LivingEntity> getReturnType() {
		return LivingEntity.class;
	}
	@Override
	public String toString(Event event, boolean b) {
		return "[(the|all)] [of] [the] armo[u]r stand[s]";
	}
	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		return true;
	}
	@Override
	protected LivingEntity[] get(Event event) {
		ArrayList<LivingEntity> entities = new ArrayList<>();
		for (LivingEntity entity : entities){
			if (entity.getType() == EntityType.ARMOR_STAND) {
				entities.add((LivingEntity)entity);
			}
		}
		return entities.toArray(new LivingEntity[entities.size()]);
	}
}