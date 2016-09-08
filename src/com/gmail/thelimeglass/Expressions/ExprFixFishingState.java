package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprFixFishingState extends SimpleExpression<PlayerFishEvent.State> {
	
	//[skellett] [fish[ing]] state
	
	public Class<? extends PlayerFishEvent.State> getReturnType() {
		return PlayerFishEvent.State.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent((Class)PlayerFishEvent.class)) {
			Skript.error((String)"You can not use Fishing State expression in any event but 'on fishing:' event!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Fishing state";
	}
	@Nullable
	protected PlayerFishEvent.State[] get(Event e) {
		return new PlayerFishEvent.State[]{((PlayerFishEvent)e).getState()};
	}
}