package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprWorldChangeFrom extends SimpleExpression<World> {
	
	//(previous|past) [changed] world
	
	public Class<? extends World> getReturnType() {
		return World.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(PlayerChangedWorldEvent.class)) {
			Skript.error("You can not use PastWorld expression in any event but world change event!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Past world";
	}
	@Nullable
	protected World[] get(Event e) {
		return new World[]{((PlayerChangedWorldEvent)e).getFrom()};
	}
}