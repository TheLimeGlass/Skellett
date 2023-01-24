package me.limeglass.skellett.elements.bossbars;

import java.util.Arrays;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprBossBarColours extends PropertyExpression<BossBar, BarColor> {

	static {
		if (Skript.classExists("org.bukkit.boss.BossBar"))
			Skript.registerExpression(ExprBossBarColours.class, BarColor.class, ExpressionType.PROPERTY, "[all [of]] [the] colo[u]r[s] (from|of) [[boss[ ]]bar] %bossbars%", "[all [of]] [the] %bossbars%'[s] [[boss[ ]]bar] colo[u]r[s]");
	}

	@Override
	public Class<? extends BarColor> getReturnType() {
		return BarColor.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		setExpr((Expression<? extends BossBar>) exprs[0]);
		return true;
	}

	@Override
	protected BarColor[] get(Event event, BossBar[] source) {
		return Arrays.stream(source).map(bossbar -> bossbar.getColor()).toArray(BarColor[]::new);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "colours of bossbars";
		return "colours of bossbars " + getExpr().toString(event, debug);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.DELETE)
			return CollectionUtils.array(BarColor.class);
		return null;
	}

	@Override
	public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
		BarColor color = delta == null ? null : (BarColor) delta[0];
		switch (mode) {
			case RESET:
			case DELETE:
				for (BossBar bossbar : getExpr().getArray(event))
					bossbar.setColor(BarColor.PURPLE);
				break;
			case SET:
				if (color == null)
					return;
				for (BossBar bossbar : getExpr().getArray(event))
					bossbar.setColor(color);
				break;
			case ADD:
			case REMOVE:
			case REMOVE_ALL:
			default:
				break;
		}
	}

}
