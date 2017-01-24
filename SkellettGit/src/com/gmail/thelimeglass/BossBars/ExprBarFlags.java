package com.gmail.thelimeglass.BossBars;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprBarFlags extends SimpleExpression<BarFlag>{
	
	//[skellett] [(the|all)] [of] [the] flag[[']s] [(in|of)] [the] [boss[ ]]bar %bossbar%
	//[skellett] %bossbar%'s flag[[']s]

	private Expression<BossBar> bar;
	@Override
	public Class<? extends BarFlag> getReturnType() {
		return BarFlag.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		bar = (Expression<BossBar>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [(the|all)] [of] [the] flag[[']s] [(in|of)] [the] [boss[ ]]bar %bossbar%";
	}
	@Override
	@Nullable
	protected BarFlag[] get(Event e) {
		if (bar == null) {
			return null;
		}
		ArrayList<BarFlag> flags = new ArrayList<BarFlag>();
		for (BarFlag flag : BarFlag.values()) {
			if (bar.getSingle(e).hasFlag(flag)) {
				flags.add(flag);
			}
		}
		return flags.toArray(new BarFlag[flags.size()]);
	}
}