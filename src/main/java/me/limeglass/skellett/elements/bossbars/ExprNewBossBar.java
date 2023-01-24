package me.limeglass.skellett.elements.bossbars;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
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
		if (Skript.classExists("org.bukkit.boss.BossBar"))
			Skript.registerExpression(ExprNewBossBar.class, BossBar.class, ExpressionType.SIMPLE, "[skellett] [a] new [boss[ ]]bar [with flag[s] %-bossbarflags%] [(with title|titled) %-string%]" +
					"[(with colo[u]r|colo[u]red) %-bossbarcolor%] [(with style|styled) %-bossbarstyle%] [keyed %-string%]");
	}

	private Expression<String> key, title;
	private Expression<BarColor> colour;
	private Expression<BarStyle> style;
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
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parsedResult) {
		flags = (Expression<BarFlag>) exprs[0];
		title = (Expression<String>) exprs[1];
		colour = (Expression<BarColor>) exprs[2];
		style = (Expression<BarStyle>) exprs[3];
		key = (Expression<String>) exprs[4];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "create a bossbar";
		return "bossbar titled " + title.toString(event, debug) + " keyed " + key.toString(event, debug) + " with flags " + flags.toString(event, debug);
	}

	@Override
	@Nullable
	protected BossBar[] get(Event event) {
		String title = this.title == null ? Skellett.cc("&a&lSkellett bossbar") : this.title.getSingle(event);
		BarColor colour = this.colour == null ? BarColor.GREEN : this.colour.getSingle(event);
		BarStyle style = this.style == null ? BarStyle.SOLID : this.style.getSingle(event);
		BarFlag[] flags = this.flags == null ? new BarFlag[0] : this.flags.getArray(event);
		if (key == null)
			return new BossBar[] {Bukkit.createBossBar(title, colour, style, flags)};
		String key = this.key.getSingle(event);
		NamespacedKey keyed = new NamespacedKey(Skellett.getInstance(), key);
		return new BossBar[] {Bukkit.createBossBar(keyed, title, colour, style, flags)};
	}

}
