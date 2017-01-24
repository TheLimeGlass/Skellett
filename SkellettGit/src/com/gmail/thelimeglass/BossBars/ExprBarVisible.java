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

public class ExprBarVisible extends SimpleExpression<Boolean>{
	
	//[the] [skellett] visib(le|ility) [(for|of)] [boss[ ]]bar %bossbar%
	//[skellett] %bossbar%'s [[boss][ ]bar] visib(le|ility)

	private Expression<BossBar> bar;
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
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
		return "[skellett] visib(le|ility) [(for|of)] [boss[ ]]bar %bossbar%";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		if (bar == null) {
			return null;
		}
		return new Boolean[]{bar.getSingle(e).isVisible()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (bar == null) {
			return;
		}
		if (mode == ChangeMode.SET) {
			bar.getSingle(e).setVisible((Boolean)delta[0]);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(Boolean.class);
		return null;
	}
}