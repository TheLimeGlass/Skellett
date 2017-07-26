package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprWhitelistedPlayers extends SimpleExpression<OfflinePlayer> {
	
	//[(the|all)] [of] [the] white[ ]list[ed] [players]
	
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
		return "[(the|all)] [of] [the] white[ ]list[ed] [players]";
	}
	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		return true;
	}
	@Override
	protected OfflinePlayer[] get(final Event e) {
		ArrayList<OfflinePlayer> white = new ArrayList<>();
		for (final OfflinePlayer p : Bukkit.getWhitelistedPlayers()) {
			if (p != null) {
				white.add((OfflinePlayer)p);
			}
		}
		return white.toArray(new OfflinePlayer[white.size()]);
	}
}