package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;


import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Main;
import com.gmail.thelimeglass.SkellettAPI.SkellettNametags;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffSetClientNametagSuffix extends Effect {

	//[skellett] [(make|set)] [client] [name]tag suffix [of] %player% to [(text|string)] %string% (for|to) %player%
	
	private Expression<Player> player;
	private Expression<String> string;
	private Expression<Player> players;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		string = (Expression<String>) e[1];
		player = (Expression<Player>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] [(make|set)] [client] [name]tag suffix [of] %player% to [(text|string)] %string% (for|to) %player%";
	}
	@Override
	protected void execute(Event e) {
		SkellettNametags.setNametagSuffix(player.getSingle(e), Main.cc(string.getSingle(e)), players.getSingle(e));
	}
}
