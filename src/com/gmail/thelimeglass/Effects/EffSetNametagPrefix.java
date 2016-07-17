package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Main;
import com.gmail.thelimeglass.SkellettAPI.SkellettNametags;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffSetNametagPrefix extends Effect {

	//[skellett] [(set|make)] prefix [of] [the] [name][ ]tag [(with|of)] [id] %string% to [(string|text)] %string%
	
	private Expression<String> nametag;
	private Expression<String> string;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		nametag = (Expression<String>) e[0];
		string = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] [(set|make)] prefix [of] [the] [name][ ]tag [(with|of)] [id] %string% to [(string|text)] %string%";
	}
	@Override
	protected void execute(Event e) {
		SkellettNametags.setNametagPrefix(nametag.getSingle(e), Main.cc(string.getSingle(e)));
	}
}