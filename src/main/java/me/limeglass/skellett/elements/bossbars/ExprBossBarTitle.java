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

public class ExprBossBarTitle extends PropertyExpression<BossBar, String> {

	static {
		if (Skript.getMinecraftVersion().isLargerThan(new Version(1, 8)))
			Skript.registerExpression(ExprBossBarTitle.class, String.class, ExpressionType.PROPERTY, "[all [of]] [the] title[s] (from|of) [[boss[ ]]bar] %bossbars%", "[all [of]] [the] %bossbars%'[s] [[boss[ ]]bar] title[s]");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		setExpr((Expression<? extends BossBar>) exprs[0]);
		return true;
	}

	@Override
	protected String[] get(Event event, BossBar[] source) {
		return Arrays.stream(source).map(bossbar -> bossbar.getTitle()).toArray(String[]::new);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "titles of bossbars";
		return "titles of bossbars " + getExpr().toString(event, debug);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode != ChangeMode.SET)
			return null;
		return CollectionUtils.array(String.class);
	}

	@Override
	public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
		String title = delta == null ? null : (String) delta[0];
		if (title == null)
			return;
		for (BossBar bossbar : getExpr().getArray(event))
			bossbar.setTitle(title);
	}

}
