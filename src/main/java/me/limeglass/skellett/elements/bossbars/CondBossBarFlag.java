package me.limeglass.skellett.elements.bossbars;

import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondBossBarFlag extends Condition {

	static {
		if (Skript.classExists("org.bukkit.boss.BossBar"))
			Skript.registerCondition(CondBossBarFlag.class, "%bossbars% (has|have) [[boss[ ]]bar] flag[s] %bossbarflags%", "%bossbars% (doesn't|does not|do not|don't) have [[boss[ ]]bar] flag[s] %bossbarflags%");
	}

	private Expression<BossBar> bossbars;
	private Expression<BarFlag> flags;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		bossbars = (Expression<BossBar>) exprs[0];
		flags = (Expression<BarFlag>) exprs[1];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "bossbar flags condition";
		return "bossbar flags " + flags.toString(event, debug) + " for bossbars " + bossbars.toString(event, debug);
	}

	@Override
	public boolean check(Event event) {
		return bossbars.check(event, bossbar -> flags.stream(event).allMatch(flag -> bossbar.hasFlag(flag)), isNegated());
	}

}
