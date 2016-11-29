package com.gmail.thelimeglass.SkellettStateExpressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemHeldEvent;

import com.gmail.thelimeglass.Utils.SkellettState;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprHotbarSwitchSlot extends SimpleExpression<Integer> {
	
	//%skellettstate% (hotbar|held) slot
	
	private Expression<SkellettState> state;
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent((Class)PlayerItemHeldEvent.class)) {
			Skript.error((String)"(hotbar|held) slot can only be used within the PlayerItemHeldEvent event!");
			return false;
		}
		state = (Expression<SkellettState>) e[0];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "new block";
	}
	@Nullable
	protected Integer[] get(Event e) {
		if (state.getSingle(e) == SkellettState.PREVIOUS) {
			return new Integer[]{((PlayerItemHeldEvent)e).getPreviousSlot()};
		} else {
			return new Integer[]{((PlayerItemHeldEvent)e).getNewSlot()};
		}
	}
}