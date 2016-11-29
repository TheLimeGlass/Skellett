package com.gmail.thelimeglass.Nametags;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffResetNametagPrefix extends Effect {

	//[skellett] reset [the] [name][ ]tag prefix [(with|of)] [id] %string%
	
	private Expression<String> nametag;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		nametag = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] reset [the] [name][ ]tag prefix [(with|of)] [id] %string%";
	}
	@Override
	protected void execute(Event e) {
		SkellettNametags.resetNametagPrefix(nametag.getSingle(e));
	}
}