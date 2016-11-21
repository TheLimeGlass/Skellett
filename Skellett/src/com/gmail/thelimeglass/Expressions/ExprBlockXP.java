package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockExpEvent;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprBlockXP extends SimpleExpression<Number>{
	
	//[dropped] block[[']s] (xp|experience)
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(BlockExpEvent.class)) {
			Skript.error("You can not use Block xp expression in any event but on block xp drop!");
			return false;
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[dropped] block[[']s] (xp|experience)";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{((BlockExpEvent)e).getExpToDrop()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number xp = (Number)delta[0];
		Number xpNow = ((BlockExpEvent)e).getExpToDrop();
		if (mode == ChangeMode.SET) {
			((BlockExpEvent)e).setExpToDrop(xp.intValue());
		} else if (mode == ChangeMode.ADD) {
			((BlockExpEvent)e).setExpToDrop(xpNow.intValue() + xp.intValue());
		} else if (mode == ChangeMode.REMOVE) {
			((BlockExpEvent)e).setExpToDrop(xpNow.intValue() - xp.intValue());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(Number.class);
		return null;
	}
}