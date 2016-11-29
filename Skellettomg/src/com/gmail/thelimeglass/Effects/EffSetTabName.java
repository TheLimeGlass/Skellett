package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffSetTabName extends Effect {
	
	//[skellett] set tab name of %player% to %string%
	
	private Expression<Player> player;
	private Expression<String> tag;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		tag = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] set tab name of %player% to %string%";
	}
	@Override
	protected void execute(Event e) {
		player.getSingle(e).setPlayerListName(tag.getSingle(e));
	}
}