package me.limeglass.skellett.elements.bossbars;

import org.bukkit.boss.BossBar;

import ch.njol.skript.Skript;
import ch.njol.skript.conditions.base.PropertyCondition;

public class CondBossBarVisible extends PropertyCondition<BossBar> {

	static {
		if (Skript.classExists("org.bukkit.boss.BossBar"))
			register(CondBossBarVisible.class, "[[boss[ ]]bar] visible", "bossbars");
	}

	@Override
	public boolean check(BossBar bossbar) {
		return bossbar.isVisible();
	}

	@Override
	protected String getPropertyName() {
		return "visible";
	}

}
