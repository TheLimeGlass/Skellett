package me.limeglass.skellett.elements.expressions;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprOperators extends SimpleExpression<OfflinePlayer> {

	static {
		Skript.registerExpression(ExprOperators.class, OfflinePlayer.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] [server] operators");
	}

	@Override
	public Class<? extends OfflinePlayer> getReturnType() {
		return OfflinePlayer.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parserResult) {
		return true;
	}

	@Override
	@Nullable
	protected OfflinePlayer[] get(Event event) {
		return Bukkit.getOperators().stream().toArray(OfflinePlayer[]::new);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "server operators";
	}

}
