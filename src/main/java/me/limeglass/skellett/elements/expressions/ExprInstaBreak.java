package me.limeglass.skellett.elements.expressions;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDamageEvent;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprInstaBreak extends SimpleExpression<Boolean> {

	static {
		Skript.registerExpression(ExprInstaBreak.class, Boolean.class, ExpressionType.SIMPLE, "[event-block] inst(ant|a) break [state]");
	}

	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!getParser().isCurrentEvent(BlockDamageEvent.class)) {
			Skript.error("You can not use Insta Break expression in any event but on block damage event!");
			return false;
		}
		return true;
	}

	@Override
	@Nullable
	protected Boolean[] get(Event event) {
		return new Boolean[]{((BlockDamageEvent)event).getInstaBreak()};
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(Boolean.class);
		return null;
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		((BlockDamageEvent)event).setInstaBreak(((boolean)delta[0]));
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "instant break";
	}

}
