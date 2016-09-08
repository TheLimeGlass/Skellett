package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprRedstoneOldCurrent extends SimpleExpression<Integer> {
	
	//old [event] [redstone] current
	
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent((Class)BlockRedstoneEvent.class)) {
			Skript.error((String)"You can not use Old Redstone Current expression in any event but on redstone changing event!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Old redstone current";
	}
	@Nullable
	protected Integer[] get(Event e) {
		return new Integer[]{((BlockRedstoneEvent)e).getOldCurrent()};
	}
}