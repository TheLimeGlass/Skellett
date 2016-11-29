package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EffForceRespawn extends Effect {

	//[skellett] [force] respawn %player%
	
	private Expression<Player> player;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] [force] respawn %player%";
	}
	@Override
	protected void execute(Event e) {
		((Player)player.getSingle(e)).spigot().respawn();
	}
}
