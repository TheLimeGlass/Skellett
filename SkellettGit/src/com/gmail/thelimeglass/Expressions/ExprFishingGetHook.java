package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Fish;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[skellett] [fish[ing]] hook")
@Config("Main.Fishing")
@FullConfig
@PropertyType(ExpressionType.SIMPLE)
@SuppressWarnings("deprecation")
public class ExprFishingGetHook extends SimpleExpression<Fish> {
	
	public Class<? extends Fish> getReturnType() {
		return Fish.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(PlayerFishEvent.class)) {
			Skript.error("You can not use Get Hook expression in any event but 'on fishing:' event!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Fishing hook";
	}
	@Nullable
	protected Fish[] get(Event e) {
		return new Fish[]{((PlayerFishEvent)e).getHook()};
	}
}