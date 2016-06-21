package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffSetSprintState extends Effect {
	
	//[set] [toggle] sprint [state] of %player% to %boolean%
	
	private Expression<Player> player;
	private Expression<Boolean> boo;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		boo = (Expression<Boolean>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[set] [toggle] sprint[ing] [state] of %player% to %boolean%";
	}
	@Override
	protected void execute(Event e) {
		player.getSingle(e).setSprinting(boo.getSingle(e));
	}
}