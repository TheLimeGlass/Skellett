package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettAPI.SkellettHolograms;

public class EffTeleportHologram extends Effect {

	//[skellett] (tp|teleport|move) holo[gram] [(with|and|of)] id %integer% [to] [location] %location%
	
	private Expression<Integer> ID;
	private Expression<Location> loc;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		ID = (Expression<Integer>) e[0];
		loc = (Expression<Location>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (tp|teleport|move) holo[gram] [(with|and|of)] id %integer% [to] [location] %location%";
	}
	@Override
	protected void execute(Event e) {
		SkellettHolograms.teleportHologram(ID.getSingle(e), loc.getSingle(e));
	}
}
