package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemHeldEvent;

import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[the] (1¦(past|previious)|2¦(new|future|present)) [changed] (hotbar|held|changed) slot")
public class ExprHotbarSwitchSlot extends SimpleExpression<Integer> {
	
	private Boolean boo = true;
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (!ScriptLoader.isCurrentEvent((Class)PlayerItemHeldEvent.class)) {
			Skript.error("changed hotbar slot can only be used within the on hotbar change event!");
			return false;
		}
		if (parser.mark == 2) {boo = false;}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[the] (1¦(past|previious)|2¦(new|future|present)) [changed] (hotbar|held|changed) slot";
	}
	@Nullable
	protected Integer[] get(Event e) {
		if (boo) {
			return new Integer[]{((PlayerItemHeldEvent)e).getPreviousSlot()};
		} else {
			return new Integer[]{((PlayerItemHeldEvent)e).getNewSlot()};
		}
	}
}