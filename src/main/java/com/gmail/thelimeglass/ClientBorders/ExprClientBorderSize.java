package com.gmail.thelimeglass.ClientBorders;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.ReflectionSyntax.ClientBorderManager;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[client [side]] [world] border size (for|of) %player%", "%player%'s [client [side]] [world] border size"})
@Config("Main.ClientWorldBorders")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprClientBorderSize extends SimpleExpression<Number>{
	
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
		return "[client [side]] [world] border size (for|of) %player%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (player != null) {
			return new Number[]{ClientBorderManager.getSize(player.getSingle(e))};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (player != null) {
			Number sizeNow = ClientBorderManager.getSize(player.getSingle(e));
			if (sizeNow == null) {
				sizeNow = 0D;
			}
			if (mode == ChangeMode.SET) {
				Number size = (Number)delta[0];
				ClientBorderManager.setSize(player.getSingle(e), size.doubleValue());
			} else if (mode == ChangeMode.ADD) {
				Number size = (Number)delta[0];
				ClientBorderManager.setSize(player.getSingle(e), sizeNow.doubleValue() + size.doubleValue());
			} else if (mode == ChangeMode.REMOVE) {
				Number size = (Number)delta[0];
				ClientBorderManager.setSize(player.getSingle(e), sizeNow.doubleValue() - size.doubleValue());
			} else if (mode == ChangeMode.RESET) {
				ClientBorderManager.setSize(player.getSingle(e), 6.0E7D); //Same number Minecraft uses when creating borders
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