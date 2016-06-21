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

public class EffSetNametagPrefix extends Effect {

	//[skellett] [(make|set)] [name]tag prefix [of] %player% to [(text|string)] %string%
	
	private Expression<Player> player;
	private Expression<String> string;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		string = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] [(make|set)] [name]tag prefix [of] %player% to [(text|string)] %string%";
	}
	@Override
	protected void execute(Event e) {
		SkellettNametags.setNametagPrefix(player.getSingle(e), Main.cc(string.getSingle(e)), null);
	}
}
