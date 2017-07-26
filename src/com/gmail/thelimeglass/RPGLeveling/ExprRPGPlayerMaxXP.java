package com.gmail.thelimeglass.RPGLeveling;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[the] rpg leveling max (level|xp|experience) of player %player%", "%player%'s rpg leveling max (level|xp|experience)"})
@Config("PluginHooks.RpgLeveling")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprRPGPlayerMaxXP extends SimpleExpression<Number>{
	
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
		return "[the] rpg leveling max (level|xp|experience) of player %player%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{me.baks.rpl.api.API.getMaxExp(player.getSingle(e))};
	}
}