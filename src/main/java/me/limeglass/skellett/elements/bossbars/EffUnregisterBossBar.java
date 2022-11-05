package me.limeglass.skellett.elements.bossbars;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Skellett;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffUnregisterBossBar extends Effect {

	static {
		if (Skript.methodExists(Bukkit.class, "getBossBars"))
			Skript.registerEffect(EffUnregisterBossBar.class, "unregister [all [of]] [the] bossbar[s] (from key|keyed) %strings%");
	}

	private Expression<String> strings;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		strings = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "unregister bossbar " + strings.toString(e, debug);
	}

	@Override
	protected void execute(Event event) {
		Arrays.stream(strings.getArray(event))
				.forEach(key -> Bukkit.removeBossBar(new NamespacedKey(Skellett.getInstance(), key)));
	}

}
