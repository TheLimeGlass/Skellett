package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprOperators extends SimpleExpression<OfflinePlayer> {
	
	//[(the|all)] [of] [the] Op[erator][(s|ed)] [players]
	
	@Override
	public boolean isSingle() {
		return false;
	}
	@Override
	public Class<? extends OfflinePlayer> getReturnType() {
		return OfflinePlayer.class;
	}
	@Override
	public String toString(Event event, boolean b) {
		return "[(the|all)] [of] [the] Op[erator][(s|ed)] [players]";
	}
	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		return true;
	}
	@Override
	protected OfflinePlayer[] get(final Event e) {
		ArrayList<OfflinePlayer> ops = new ArrayList<>();
		for (final OfflinePlayer p : Bukkit.getOperators()) {
			if (p != null) {
				ops.add((OfflinePlayer)p);
			}
		}
		return ops.toArray(new OfflinePlayer[ops.size()]);
	}
}