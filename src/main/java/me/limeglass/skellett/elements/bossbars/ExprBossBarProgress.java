package me.limeglass.skellett.elements.bossbars;

import java.util.Arrays;

import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Version;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprBossBarProgress extends PropertyExpression<BossBar, Double> {

	static {
		if (Skript.getMinecraftVersion().isLargerThan(new Version(1, 8)))
			Skript.registerExpression(ExprBossBarProgress.class, Double.class, ExpressionType.PROPERTY, "[all [of]] [the] progress (from|of) [[boss[ ]]bar] %bossbars%", "[all [of]] [the] %bossbars%'[s] [boss[ ]]bar progress");
	}

	@Override
	public Class<? extends Double> getReturnType() {
		return Double.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		setExpr((Expression<? extends BossBar>) exprs[0]);
		return true;
	}

	@Override
	protected Double[] get(Event event, BossBar[] source) {
		return Arrays.stream(source).map(bossbar -> bossbar.getProgress()).toArray(Double[]::new);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "progress of bossbars";
		return "progress of bossbars " + getExpr().toString(event, debug);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		return CollectionUtils.array(Number.class);
	}

	@Override
	public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
		Double progress = delta == null ? null : ((Number) delta[0]).doubleValue();
		switch (mode) {
			case RESET:
			case DELETE:
				for (BossBar bossbar : getExpr().getArray(event))
					bossbar.setProgress(1);
				break;
			case SET:
				if (progress == null)
					return;
				for (BossBar bossbar : getExpr().getArray(event))
					bossbar.setProgress(progress);
				break;
			case ADD:
				if (progress == null)
					return;
				for (BossBar bossbar : getExpr().getArray(event)) {
					double existing = bossbar.getProgress();
					bossbar.setProgress(existing + progress);
				}
				break;
			case REMOVE:
			case REMOVE_ALL:
				if (progress == null)
					return;
				for (BossBar bossbar : getExpr().getArray(event)) {
					double existing = bossbar.getProgress();
					bossbar.setProgress(existing - progress);
				}
				break;
			default:
				break;
		}
	}

}
