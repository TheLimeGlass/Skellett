package me.limeglass.skellett.elements.conditions;

import org.bukkit.entity.Player;

import ch.njol.skript.conditions.base.PropertyCondition;

public class ConfSleepIgnored extends PropertyCondition<Player> {

	static {
		register(ConfSleepIgnored.class, "sleep ignored", "players");
	}

	@Override
	public boolean check(Player player) {
		return player.isSleepingIgnored();
	}

	@Override
	protected String getPropertyName() {
		return "sleep ignored";
	}

}
