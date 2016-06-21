package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffSetClientTime extends Effect {
	
	//[set] [client] time of [player] %player% to %integer%
	
	private Expression<Player> player;
	private Expression<Integer> time;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		time = (Expression<Integer>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[set] [client] time of [player] %player% to %integer%";
	}
	@Override
	protected void execute(Event e) {
		player.getSingle(e).setPlayerTime((Integer)time.getSingle(e), true);
	}
}