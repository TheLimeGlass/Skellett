package com.gmail.thelimeglass.Nametags;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] [(set|make)] prefix [of] [the] [name][ ]tag [(with|of)] [id] %string% to [(string|text)] %string%")
@Config("Main.Nametags")
@FullConfig
public class EffSetNametagPrefix extends Effect {
	
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
		SkellettNametags.setNametagPrefix(nametag.getSingle(e), Skellett.cc(string.getSingle(e)));
	}
}