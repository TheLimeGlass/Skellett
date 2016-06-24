package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffSetWhitelist extends Effect {

	//[set] white[ ]list [to] %boolean%
	
	private Expression<Boolean> boo;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		boo = (Expression<Boolean>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[set] white[ ]list [to] %boolean%";
	}
	@Override
	protected void execute(Event e) {
		Bukkit.setWhitelist(boo.getSingle(e));
	}
}