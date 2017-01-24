package com.gmail.thelimeglass.BossBars;

import javax.annotation.Nullable;

import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprBarProgress extends SimpleExpression<Number>{
	
	//[the] [skellett] progress of [boss[ ]]bar %bossbar%
	//[skellett] %bossbar%'s [[boss][ ]bar] progress

	private Expression<BossBar> bar;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		bar = (Expression<BossBar>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] progress of [boss[ ]]bar %bossbar%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (bar == null) {
			return null;
		}
		return new Number[]{bar.getSingle(e).getProgress()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (bar == null) {
			return;
		}
		Number num = (Number)delta[0];
		if (mode == ChangeMode.SET) {
			bar.getSingle(e).setProgress(num.doubleValue());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}