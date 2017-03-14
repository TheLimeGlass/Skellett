package com.gmail.thelimeglass.Eggwars;

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
import es.minetsii.eggwars.objects.EwPlayer;

@Syntax("[the] player [(from|of)] egg[ ]wars player %player%")
@Config("PluginHooks.Eggwars")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprEggwarsGetPlayer extends SimpleExpression<Player>{
	
	private Expression<EwPlayer> player;
	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<EwPlayer>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] player [(from|of)] egg[ ]wars player %player%";
	}
	@Override
	@Nullable
	protected Player[] get(Event e) {
		if (player != null) {
			return new Player[]{player.getSingle(e).getPlayer()};
		}
		return null;
	}
}