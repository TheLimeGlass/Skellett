package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettAPI.SkellettNametags;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffResetNametagSuffix extends Effect {

	//[skellett] reset [name]tag suffix [of] %player%
	
	private Expression<Player> player;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] reset [name]tag suffix [of] %player%";
	}
	@Override
	protected void execute(Event e) {
		SkellettNametags.resetNametagSuffix(player.getSingle(e), null);
	}
}