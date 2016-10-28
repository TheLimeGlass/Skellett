package com.gmail.thelimeglass.Expressions;

import org.bukkit.entity.Entity;
import javax.annotation.Nullable;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

public class ExprEntityID extends SimplePropertyExpression<Entity, Number> {
	
	-	//[the] [entity] [number] id (of|from) %entity%		+	@Override
	-	//%entity%'s [entity] [number] id
	
	@Override
	@Nullable
	public Number convert(Entity ent) {
		return ent.getEntityId();
		
	}
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}



	@Override
	protected String getPropertyName() {
		return "[the] [entity] [number] id (of|from) %entities%";
	}
	
}
