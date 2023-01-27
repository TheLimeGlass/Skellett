package me.limeglass.skellett.elements.bossbars;

import java.util.Arrays;

import org.bukkit.boss.BarStyle;
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

public class ExprBossBarStyles extends PropertyExpression<BossBar, BarStyle> {

	static {
		if (Skript.classExists("org.bukkit.boss.BossBar"))
			Skript.registerExpression(ExprBossBarStyles.class, BarStyle.class, ExpressionType.PROPERTY, "[all [of]] [the] styles (from|of) [[boss[ ]]bar] %bossbars%", "[all [of]] [the] %bossbars%'[s] [[boss[ ]]bar] styles");
	}

	@Override
	public Class<? extends BarStyle> getReturnType() {
		return BarStyle.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		setExpr((Expression<? extends BossBar>) exprs[0]);
		return true;
	}

	@Override
	protected BarStyle[] get(Event event, BossBar[] source) {
		return Arrays.stream(source).map(bossbar -> bossbar.getStyle()).toArray(BarStyle[]::new);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "styles of bossbars";
		return "styles of bossbars " + getExpr().toString(event, debug);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.DELETE)
			return CollectionUtils.array(BarStyle.class);
		return null;
	}

	@Override
	public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
		BarStyle style = delta == null ? null : (BarStyle) delta[0];
		switch (mode) {
			case RESET:
			case DELETE:
				for (BossBar bossbar : getExpr().getArray(event))
					bossbar.setStyle(BarStyle.SOLID);
				break;
			case SET:
				if (style == null)
					return;
				for (BossBar bossbar : getExpr().getArray(event))
					bossbar.setStyle(style);
				break;
			case ADD:
			case REMOVE:
			case REMOVE_ALL:
			default:
				break;
		}
	}

}
