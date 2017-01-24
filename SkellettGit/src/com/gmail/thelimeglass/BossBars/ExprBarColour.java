package com.gmail.thelimeglass.BossBars;

import javax.annotation.Nullable;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprBarColour extends SimpleExpression<BarColor>{
	
	//[the] [skellett] colo[u]r of [boss[ ]]bar %bossbar%
	//[skellett] %bossbar%'s [[boss][ ]bar] colo[u]r

	private Expression<BossBar> bar;
	@Override
	public Class<? extends BarColor> getReturnType() {
		return BarColor.class;
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
		return "[skellett] colo[u]r of [boss[ ]]bar %bossbar%";
	}
	@Override
	@Nullable
	protected BarColor[] get(Event e) {
		if (bar == null) {
			return null;
		}
		return new BarColor[]{bar.getSingle(e).getColor()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (bar == null) {
			return;
		}
		BarColor colour = (BarColor)delta[0];
		if (mode == ChangeMode.SET) {
			bar.getSingle(e).setColor(colour);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(BarColor.class);
		return null;
	}
}