package me.limeglass.skellett.elements.bossbars;

import java.util.Arrays;
import java.util.stream.Stream;

import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprBossBarFlags extends SimpleExpression<BarFlag> {

	static {
		Skript.registerExpression(ExprBossBarFlags.class, BarFlag.class, ExpressionType.PROPERTY, "[all [of]] [the] [[boss[ ]]bar] flags (from|of) %bossbars%", "[all [of]] [the] %bossbars%'[s] [[boss[ ]]bar] flags");
	}

	private Expression<BossBar> bossbars;

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends BarFlag> getReturnType() {
		return BarFlag.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		bossbars = (Expression<BossBar>) exprs[0];
		return true;
	}

	@Override
	protected @Nullable BarFlag[] get(Event event) {
		return bossbars.stream(event).flatMap(bossbar -> getFlags(bossbar)).toArray(BarFlag[]::new);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "flags of bossbars " + bossbars.toString(event, debug);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		return CollectionUtils.array(BarFlag[].class, BarFlag.class);
	}

	@Override
	public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
		BarFlag[] flags = delta == null ? null : (BarFlag[]) delta;
		if (flags == null)
			return;
		switch (mode) {
			case ADD:
				for (BossBar bossbar : bossbars.getArray(event)) {
					for (BarFlag flag : flags)
						bossbar.addFlag(flag);
				}
				break;
			case RESET:
			case DELETE:
				for (BossBar bossbar : bossbars.getArray(event))
					getFlags(bossbar).forEach(flag -> bossbar.removeFlag(flag));
				break;
			case REMOVE:
			case REMOVE_ALL:
				for (BossBar bossbar : bossbars.getArray(event)) {
					for (BarFlag flag : flags)
						bossbar.removeFlag(flag);
				}
				break;
			case SET:
				for (BossBar bossbar : bossbars.getArray(event)) {
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
