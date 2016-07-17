package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprOfflinePlayers extends SimpleExpression<OfflinePlayer> {
	
	//[(the|all)] [of] [the] offline[ ]player[s]
	
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
		return "[(the|all)] [of] [the] offline[ ]player[s]";
	}
	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		return true;
	}
	@Override
	protected OfflinePlayer[] get(final Event e) {
		ArrayList<OfflinePlayer> offlineplayers = new ArrayList<>();
		for (final OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			if (p != null) {
				offlineplayers.add((OfflinePlayer)p);
			}
		}
		return offlineplayers.toArray(new OfflinePlayer[offlineplayers.size()]);
	}
}