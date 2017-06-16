package com.gmail.thelimeglass.Feudal;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import us.forseth11.feudal.core.Feudal;
import us.forseth11.feudal.kingdoms.Kingdom;

public class ExprFeudalKingdomHome extends SimpleExpression<Location>{
	
	//Feudal kingdom home of %kingdom%
	//%kingdom%'s Feudal kingdom home
	
	Feudal feudal = Feudal.getPlugin();
	private Expression<Kingdom> kingdom;
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
		kingdom = (Expression<Kingdom>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "Feudal kingdom hom of %kingdom%";
	}
	@Override
	@Nullable
	protected Location[] get(Event e) {
		return new Location[]{kingdom.getSingle(e).getHome()};
	}
}