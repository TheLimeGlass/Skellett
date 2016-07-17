package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettAPI.SkellettNametags;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffRemovePlayerNametag extends Effect {

	//[skellett] remove %player% from [the] [name][ ]tag [(with|of)] [id] %string%
	
	private Expression<Player> player;
	private Expression<String> nametag;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		nametag = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] remove %player% from [the] [name][ ]tag [(with|of)] [id] %string%";
	}
	@Override
	protected void execute(Event e) {
		SkellettNametags.removePlayerNametag(player.getSingle(e), nametag.getSingle(e));
	}
}
