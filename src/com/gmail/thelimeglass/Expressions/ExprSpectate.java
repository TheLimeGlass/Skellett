package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprSpectate extends SimpleExpression<Entity>{
	
	//(spec[tat(e|or|ing)]|view[ing]) [(target|state)] of %player%
	//%player%'s (spec[tat(e|or|ing)]|view[ing]) [(target|state)]
	
	private Expression<Player> player;
	@Override
	public Class<? extends Entity> getReturnType() {
		return Entity.class;
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
		return "(spec[tat(e|ing)]|view[ing]) [(target|state)] of %player%";
	}
	@Override
	@Nullable
	protected Entity[] get(Event e) {
		if (player.getSingle(e).getGameMode() == GameMode.SPECTATOR) {
			return new Entity[]{player.getSingle(e).getSpectatorTarget()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (player.getSingle(e).getGameMode() != GameMode.SPECTATOR) {
				player.getSingle(e).setGameMode(GameMode.SPECTATOR);
			}
			player.getSingle(e).setSpectatorTarget((Entity)delta[0]);
		} else if (mode == ChangeMode.RESET || mode == ChangeMode.REMOVE || mode == ChangeMode.DELETE) {
			if (player.getSingle(e).getGameMode() == GameMode.SPECTATOR) {
				player.getSingle(e).setSpectatorTarget(null);
			} else {
				return;
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.REMOVE || mode == ChangeMode.DELETE)
			return CollectionUtils.array(Entity.class);
		return null;
	}
}