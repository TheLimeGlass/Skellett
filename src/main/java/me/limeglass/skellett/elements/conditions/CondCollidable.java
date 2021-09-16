package me.limeglass.skellett.elements.conditions;

import org.bukkit.entity.LivingEntity;

import ch.njol.skript.Skript;
import ch.njol.skript.conditions.base.PropertyCondition;

public class CondCollidable extends PropertyCondition<LivingEntity> {

	static {
		if (Skript.methodExists(LivingEntity.class, "isCollidable"))
			register(CondCollidable.class, "in collidable", "livingentities");
	}

	@Override
	public boolean check(LivingEntity entity) {
		return entity.isCollidable();
	}

	@Override
	protected String getPropertyName() {
		return "collidable";
	}

}
