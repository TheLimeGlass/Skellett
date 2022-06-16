package me.limeglass.skellett.elements.bossbars;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
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

public class ExprNewBossBar extends SimpleExpression<BossBar> {

	static {
		Skript.registerExpression(ExprNewBossBar.class, BossBar.class, ExpressionType.SIMPLE, "[skellett] [create] [a] new [boss[ ]]bar [with flag[s] %-bossbarflags%]");
	}

	private Expression<BarFlag> flags;

	@Override
	public Class<? extends BossBar> getReturnType() {
		return BossBar.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		flags = (Expression<BarFlag>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [create] [a] new [boss[ ]]bar";
	}

	@Override
	@Nullable
	protected BossBar[] get(Event e) {
		BossBar bar = Bukkit.createBossBar(Skellett.cc("&a&lSkellett bossbar"), BarColor.GREEN, BarStyle.SOLID, new BarFlag[0]);
		if (flags != null)
			bar = Bukkit.createBossBar(Skellett.cc("&a&lSkellett bossbar"), BarColor.GREEN, BarStyle.SOLID, flags.getArray(e));
		return new BossBar[]{bar};
	}

}
