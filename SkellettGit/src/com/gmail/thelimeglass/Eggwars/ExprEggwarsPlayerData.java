package com.gmail.thelimeglass.Eggwars;

import javax.annotation.Nullable;

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
import es.minetsii.eggwars.objects.DataPlayer;
import es.minetsii.eggwars.objects.EwPlayer;

@Syntax({"[the] egg[ ]wars [(player|game)] data of player %ewplayer%", "%player%'s egg[ ]war[s] [(player|game)] data"})
@Config("PluginHooks.Eggwars")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
@RegisterSimpleEnum(ExprClass=DataPlayer.class, value="ewdata")
public class ExprEggwarsPlayerData extends SimpleExpression<DataPlayer>{
	
	private Expression<EwPlayer> player;
	@Override
	public Class<? extends DataPlayer> getReturnType() {
		return DataPlayer.class;
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
		return "[the] egg[ ]wars [(player|game)] data of player %ewplayer%";
	}
	@Override
	@Nullable
	protected DataPlayer[] get(Event e) {
		if (player != null) {
			return new DataPlayer[]{player.getSingle(e).getData()};
		}
		return null;
	}
}