package com.gmail.thelimeglass.Expressions;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(the|all)] [of] [the] offline[ ]player[s]")
@Config("OfflinePlayers")
@PropertyType(ExpressionType.SIMPLE)
public class ExprOfflinePlayers extends SimpleExpression<OfflinePlayer> {
	
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
		return Bukkit.getOfflinePlayers();
	}
}