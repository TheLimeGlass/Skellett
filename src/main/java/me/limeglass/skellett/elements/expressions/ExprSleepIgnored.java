package me.limeglass.skellett.elements.expressions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class ExprSleepIgnored extends SimplePropertyExpression<Player, Boolean> {

	static {
		if (Skript.methodExists(Player.class, "setSleepingIgnored", Boolean.class))
			register(ExprSleepIgnored.class, Boolean.class, "ignored sleep[ing] [state]", "players");
	}

	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	@Nullable
	public Boolean convert(Player player) {
		return player.isSleepingIgnored();
	}

	@Override
	protected String getPropertyName() {
		return "ignored sleeping state";
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.DELETE)
			return CollectionUtils.array(Boolean.class);
		return null;
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		boolean change = (boolean) delta[0];
		if (mode == ChangeMode.SET) {
			for (Player player : getExpr().getArray(event))
				player.setSleepingIgnored(change);
		} else {
			for (Player player : getExpr().getArray(event))
				player.setSleepingIgnored(false);
		}
	}

}
