package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
	
public class ExprRedstoneNewCurrent extends SimpleExpression<Integer> {
	
	//new [event] [redstone] current
	
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent((Class)BlockRedstoneEvent.class)) {
			Skript.error((String)"You can not use New Redstone Current expression in any event but on redstone changing event!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "New redstone current";
	}
	@Nullable
	protected Integer[] get(Event e) {
		return new Integer[]{((BlockRedstoneEvent)e).getNewCurrent()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET)
			((BlockRedstoneEvent)e).setNewCurrent(((Integer)delta[0]));
		if (mode == ChangeMode.ADD)
			((BlockRedstoneEvent)e).setNewCurrent((((BlockRedstoneEvent)e).getNewCurrent() + (Integer)delta[0]));
		if (mode == ChangeMode.REMOVE)
			((BlockRedstoneEvent)e).setNewCurrent((((BlockRedstoneEvent)e).getNewCurrent() - (Integer)delta[0]));
		if (mode == ChangeMode.RESET)
			((BlockRedstoneEvent)e).setNewCurrent(((BlockRedstoneEvent)e).getNewCurrent());
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET)
			return CollectionUtils.array(Integer.class);
		return null;
	}
}