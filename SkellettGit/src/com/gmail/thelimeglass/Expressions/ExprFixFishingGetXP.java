package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax("[the] [fish[ing]] (xp|experience) [earned]")
@Config("Main.Fishing")
@FullConfig
@PropertyType(ExpressionType.SIMPLE)
public class ExprFixFishingGetXP extends SimpleExpression<Number> {
	
	public Class<? extends Number> getReturnType() {
		return Number.class;
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
	protected Number[] get(Event e) {
		return new Number[]{((PlayerFishEvent)e).getExpToDrop()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number xp = (Number)delta[0];
		Number xpNow = ((PlayerFishEvent)e).getExpToDrop();
		if (mode == ChangeMode.SET) {
			((PlayerFishEvent)e).setExpToDrop(xp.intValue());
		}
		if (mode == ChangeMode.ADD) {
			((PlayerFishEvent)e).setExpToDrop(xpNow.intValue() + xp.intValue());
		}
		if (mode == ChangeMode.REMOVE) {
			((PlayerFishEvent)e).setExpToDrop(xpNow.intValue() - xp.intValue());
		}
		if (mode == ChangeMode.RESET) {
			((PlayerFishEvent)e).setExpToDrop(xpNow.intValue());
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}