package me.limeglass.skellett.elements.bossbars;

import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import me.limeglass.skellett.lang.SetEffect;

public class SetBossBarVisible extends SetEffect<BossBar> {

	static {
		if (Skript.classExists("org.bukkit.boss.BossBar"))
			register(SetBossBarVisible.class, "[[boss][ ]bar] visib(le|ility)", "bossbars");
	}

	@Override
	protected String getPropertyName() {
		return "visibility of bossbars";
	}

	@Override
	protected void execute(Event event) {
		apply(event, (bossbar, value) -> bossbar.setVisible(value));
	}

}
