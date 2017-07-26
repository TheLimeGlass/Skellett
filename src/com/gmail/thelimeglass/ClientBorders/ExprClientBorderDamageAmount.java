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

@Syntax({"[client [side]] [world] border damage [amount] (for|of) %player%", "%player%'s [client [side]] [world] border damage [amount]"})
@Config("Main.ClientWorldBorders")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprClientBorderDamageAmount extends SimpleExpression<Number>{
	
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
		return "[client [side]] [world] border damage [amount] (for|of) %player%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (player != null) {
			return new Number[]{ClientBorderManager.getDamageAmount(player.getSingle(e))};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (player != null) {
			Number amountNow = ClientBorderManager.getDamageAmount(player.getSingle(e));
			if (amountNow == null) {
				amountNow = 0.2D;
			}
			if (mode == ChangeMode.SET) {
				Number amount = (Number)delta[0];
				ClientBorderManager.setDamageAmount(player.getSingle(e), amount.doubleValue());
			} else if (mode == ChangeMode.ADD) {
				Number amount = (Number)delta[0];
				ClientBorderManager.setDamageAmount(player.getSingle(e), amountNow.doubleValue() + amount.doubleValue());
			} else if (mode == ChangeMode.REMOVE) {
				Number amount = (Number)delta[0];
				ClientBorderManager.setDamageAmount(player.getSingle(e), amountNow.doubleValue() - amount.doubleValue());
			} else if (mode == ChangeMode.RESET) {
				ClientBorderManager.setDamageAmount(player.getSingle(e), 0.2D); //Same number Minecraft uses when creating borders
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