package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprSprintState extends SimpleExpression<Boolean>{
	
	//(sprint|run)[ing] [state] of %player%
	
	private Expression<Player> player;
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "(sprint|run)[ing] [state] of %player%";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		return new Boolean[]{player.getSingle(e).isSprinting()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET)
			player.getSingle(e).setSprinting((Boolean)delta[0]);
		if (mode == ChangeMode.RESET)
			player.getSingle(e).setSprinting(false);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET)
			return CollectionUtils.array(Boolean.class);
		return null;
	}
}