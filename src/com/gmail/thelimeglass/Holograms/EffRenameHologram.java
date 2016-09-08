package com.gmail.thelimeglass.Holograms;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettAPI.SkellettHolograms;

public class EffRenameHologram extends Effect {

	//[skellett] [set] [re]name holo[gram] [(with|and|of)] id %integer% [(to|with)] [(string|text)] %string%
	
	private Expression<Integer> ID;
	private Expression<String> string;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		ID = (Expression<Integer>) e[0];
		string = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] [set] [re]name holo[gram] [(with|and|of)] id %integer% [(to|with)] [(string|text)] %string%";
	}
	@Override
	protected void execute(Event e) {
		SkellettHolograms.renameHologram(ID.getSingle(e), string.getSingle(e));
	}
}
