package com.gmail.thelimeglass.Feudal;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import us.forseth11.feudal.core.Feudal;
import us.forseth11.feudal.kingdoms.Kingdom;

public class ExprFeudalPlayerKingdom extends SimpleExpression<Kingdom>{
	
	//Feudal kingdom of %player%
	//%player%'s Feudal kingdom
	
	Feudal feudal = Feudal.getPlugin();
	private Expression<Player> player;
	@Override
	public Class<? extends Kingdom> getReturnType() {
		return Kingdom.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "Feudal kingdom of %player%";
	}
	@Override
	@Nullable
	protected Kingdom[] get(Event e) {
		for(Kingdom kingdom : Feudal.getKingdoms()){
			if(kingdom.isMember(player.getSingle(e).getUniqueId().toString())){
				return new Kingdom[]{kingdom};
			}
		}
		return null;
	}
}