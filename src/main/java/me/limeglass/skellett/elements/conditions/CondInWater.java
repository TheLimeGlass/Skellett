package me.limeglass.skellett.elements.conditions;

import org.bukkit.entity.LivingEntity;

import ch.njol.skript.Skript;
import ch.njol.skript.conditions.base.PropertyCondition;

public class CondInWater extends PropertyCondition<LivingEntity> {

	static {
		if (Skript.methodExists(LivingEntity.class, "isInWater"))
			register(CondInWater.class, "in water", "livingentities");
	}

	@Override
	public boolean check(LivingEntity entity) {
		return entity.isInWater();
	}

	@Override
	protected String getPropertyName() {
		return "sleep ignored";
	}

}
