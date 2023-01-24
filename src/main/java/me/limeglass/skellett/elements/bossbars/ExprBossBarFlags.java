package me.limeglass.skellett.elements.bossbars;

import java.util.Arrays;
import java.util.stream.Stream;

import org.bukkit.boss.BarFlag;
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

public class ExprBossBarFlags extends PropertyExpression<BossBar, BarFlag> {

	static {
		if (Skript.classExists("org.bukkit.boss.BossBar"))
			Skript.registerExpression(ExprBossBarFlags.class, BarFlag.class, ExpressionType.PROPERTY, "[all [of]] [the] flags (from|of) [[boss[ ]]bar] %bossbars%", "[all [of]] [the] %bossbars%'[s] [[boss[ ]]bar] flags");
	}

	@Override
	public Class<? extends BarFlag> getReturnType() {
		return BarFlag.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		setExpr((Expression<? extends BossBar>) exprs[0]);
		return true;
	}

	@Override
	protected BarFlag[] get(Event event, BossBar[] source) {
		return Arrays.stream(source).flatMap(bossbar -> getFlags(bossbar)).toArray(BarFlag[]::new);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "flags of bossbars";
		return "flags of bossbars " + getExpr().toString(event, debug);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		return CollectionUtils.array(BarFlag[].class, BarFlag.class);
	}

	@Override
	public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
		BarFlag[] flags = delta == null ? null : (BarFlag[]) delta;
		switch (mode) {
			case ADD:
				if (flags == null)
					return;
				for (BossBar bossbar : getExpr().getArray(event)) {
					for (BarFlag flag : flags)
						bossbar.addFlag(flag);
				}
				break;
			case RESET:
			case DELETE:
				for (BossBar bossbar : getExpr().getArray(event))
					getFlags(bossbar).forEach(flag -> bossbar.removeFlag(flag));
				break;
			case REMOVE:
			case REMOVE_ALL:
				if (flags == null)
					return;
				for (BossBar bossbar : getExpr().getArray(event)) {
					for (BarFlag flag : flags)
						bossbar.removeFlag(flag);
				}
				break;
			case SET:
				if (flags == null)
					return;
				for (BossBar bossbar : getExpr().getArray(event)) {
					getFlags(bossbar).forEach(flag -> bossbar.removeFlag(flag));
					for (BarFlag flag : flags)
						bossbar.addFlag(flag);
				}
				break;
			default:
				break;
		}
	}

	private Stream<BarFlag> getFlags(BossBar bossbar) {
		return Arrays.stream(BarFlag.values()).filter(flag -> bossbar.hasFlag(flag));
	}

}
