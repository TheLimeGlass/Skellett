package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettAPI.SkellettHolograms;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprGetHologramLocation extends SimpleExpression<Location>{
	
	//[get] location of holo[gram] [with] [id] %integer%
	
	private Expression<Integer> ID;
	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		ID = (Expression<Integer>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[get] location of holo[gram] [with] [id] %integer%";
	}
	@Override
	@Nullable
	protected Location[] get(Event e) {
		return new Location[]{SkellettHolograms.getHologramLocation(ID.getSingle(e))};
	}
}