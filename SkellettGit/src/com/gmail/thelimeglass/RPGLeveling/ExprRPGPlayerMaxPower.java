package com.gmail.thelimeglass.RPGLeveling;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[the] rpg leveling max power of player %player%", "%player%'s rpg leveling max power"})
@Config("PluginHooks.RpgLeveling")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprRPGPlayerMaxPower extends SimpleExpression<Number>{
	
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
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] rpg leveling max power of player %player%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{me.baks.rpl.api.API.getMaxPower(player.getSingle(e))};
	}
}