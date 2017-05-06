package com.gmail.thelimeglass.Expressions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[(the|all)] [of] [the] hidden players (of|from) %player%", "[all] [of] %player%'s hidden players"})
@Config("HiddenPlayers")
@PropertyType(ExpressionType.SIMPLE)
public class ExprHidenplayers extends SimpleExpression<Player> {
	
	private Expression<Player> player;
	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parse) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(Event event, boolean b) {
		return "[(the|all)] [of] [the] hidden players (of|from) %player%";
	}
	@Override
	protected Player[] get(final Event e) {
		return player.getSingle(e).spigot().getHiddenPlayers().toArray(new Player[player.getSingle(e).spigot().getHiddenPlayers().size()]);
	}
}