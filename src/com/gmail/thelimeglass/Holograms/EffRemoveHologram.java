package com.gmail.thelimeglass.Holograms;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettAPI.SkellettHolograms;

public class EffRemoveHologram extends Effect {

	//[skellett] (delete|remove|despawn|clear|kill) holo[gram] [with] id %integer%
	
	private Expression<Integer> integer;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		integer = (Expression<Integer>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (delete|remove|despawn|clear|kill) holo[gram] [with] id %integer%";
	}
	@Override
	protected void execute(Event e) {
		SkellettHolograms.removeHologram(integer.getSingle(e));
	}
}
