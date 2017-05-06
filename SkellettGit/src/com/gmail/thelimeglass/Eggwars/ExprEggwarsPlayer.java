package com.gmail.thelimeglass.Eggwars;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterSimpleEnum;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import es.minetsii.eggwars.API.EggWarsAPI;
import es.minetsii.eggwars.objects.EwPlayer;

@Syntax({"[the] egg[ ]wars player [(from|of)] %player%", "%player%'s egg[ ]war[s] player"})
@Config("PluginHooks.Eggwars")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
@RegisterSimpleEnum(ExprClass=EwPlayer.class, value="ewplayer")
public class ExprEggwarsPlayer extends SimpleExpression<EwPlayer>{
	
	private Expression<Player> player;
	@Override
	public Class<? extends EwPlayer> getReturnType() {
		return EwPlayer.class;
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
		return "%player%'s egg[ ]war[s] player";
	}
	@Override
	@Nullable
	protected EwPlayer[] get(Event e) {
		if (player != null) {
			return new EwPlayer[]{EggWarsAPI.getEggWarsPlayer(player.getSingle(e))};
		}
		return null;
	}
}