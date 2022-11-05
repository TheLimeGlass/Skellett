package me.limeglass.skellett.elements.bossbars;

import java.util.Arrays;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Skellett;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprBossBars extends SimpleExpression<BossBar> {

	static {
		if (Skript.methodExists(Bukkit.class, "getBossBars"))
			Skript.registerExpression(ExprBossBars.class, BossBar.class, ExpressionType.SIMPLE, "[all [of]] [the] bossbars", "bossbar[s] (from key|keyed) %strings%");
	}

	private Expression<String> strings;

	@Override
	public Class<? extends BossBar> getReturnType() {
		return BossBar.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parsedResult) {
		strings = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "bossbars";
	}

	@Override
	@Nullable
	protected BossBar[] get(Event event) {
		if (strings == null)
			return Stream.of(Bukkit.getBossBars()).toArray(BossBar[]::new);
		return Arrays.stream(strings.getArray(event))
				.map(key -> Bukkit.getBossBar(new NamespacedKey(Skellett.getInstance(), key)))
				.toArray(BossBar[]::new);
	}

}
