package com.gmail.thelimeglass.ClientBorders;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.versionControl.ClientBorderManager;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[client [side]] [world] border warning distance (for|of) %player%", "%player%'s [client [side]] [world] border warning distance"})
@Config("Main.ClientWorldBorders")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprClientBorderWarningDistance extends SimpleExpression<Number>{
	
	private Expression<Player> player;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[client [side]] [world] border warning distance (for|of) %player%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (player != null) {
			return new Number[]{ClientBorderManager.getWarningDistance(player.getSingle(e))};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (player != null) {
			Integer distanceNow = ClientBorderManager.getWarningDistance(player.getSingle(e));
			if (distanceNow == null) {
				distanceNow = 5;
			}
			if (mode == ChangeMode.SET) {
				Number distance = (Number)delta[0];
				ClientBorderManager.setWarningDistance(player.getSingle(e), distance.intValue());
			} else if (mode == ChangeMode.ADD) {
				Number distance = (Number)delta[0];
				ClientBorderManager.setWarningDistance(player.getSingle(e), distanceNow + distance.intValue());
			} else if (mode == ChangeMode.REMOVE) {
				Number distance = (Number)delta[0];
				ClientBorderManager.setWarningDistance(player.getSingle(e), distanceNow - distance.intValue());
			} else if (mode == ChangeMode.RESET) {
				ClientBorderManager.setWarningDistance(player.getSingle(e), 5); //Same number Minecraft uses when creating borders
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}