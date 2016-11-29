package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
	
public class ExprFixFishingGetXP extends SimpleExpression<Integer> {
	
	//[fish[ing]] (xp|experience) [earned]
	
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(PlayerFishEvent.class)) {
			Skript.error("You can not use Get XP expression in any event but 'on fishing:' event!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Fishing XP";
	}
	@Nullable
	protected Integer[] get(Event e) {
		return new Integer[]{((PlayerFishEvent)e).getExpToDrop()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET)
			((PlayerFishEvent)e).setExpToDrop(((Integer)delta[0]));
		if (mode == ChangeMode.ADD)
			((PlayerFishEvent)e).setExpToDrop((((PlayerFishEvent)e).getExpToDrop() + (Integer)delta[0]));
		if (mode == ChangeMode.REMOVE)
			((PlayerFishEvent)e).setExpToDrop((((PlayerFishEvent)e).getExpToDrop() - (Integer)delta[0]));
		if (mode == ChangeMode.RESET)
			((PlayerFishEvent)e).setExpToDrop(((PlayerFishEvent)e).getExpToDrop());
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET)
			return CollectionUtils.array(Integer.class);
		return null;
	}
}