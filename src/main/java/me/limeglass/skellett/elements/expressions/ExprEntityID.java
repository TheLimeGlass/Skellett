package me.limeglass.skellett.elements.expressions;

import org.bukkit.entity.Entity;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

public class ExprEntityID extends SimplePropertyExpression<Entity, Number> {

	static {
		register(ExprEntityID.class, Number.class, "[entity] ID", "entities");
	}

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	@Nullable
	public Number convert(Entity entity) {
		return entity.getEntityId();
	}

	@Override
	protected String getPropertyName() {
		return "entity ID";
	}

}
