package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettAPI.SkellettClientWorldborder;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffSetClientWorldBorder extends Effect {
	
	//[skellett] [set] (client|player) [world][ ]border [of] %player% to [radius] %integer%
	
	private Expression<Player> player;
	private Expression<Integer> radius;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		radius = (Expression<Integer>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] [set] (client|player) [world][ ]border [of] %player% to [radius] %integer%";
	}
	@Override
	protected void execute(Event e) {
		SkellettClientWorldborder.setBorder(player.getSingle(e), radius.getSingle(e));
	}
}
