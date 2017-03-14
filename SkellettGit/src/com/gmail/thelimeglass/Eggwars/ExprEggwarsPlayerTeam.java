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
import es.minetsii.eggwars.objects.EwPlayer;
import es.minetsii.eggwars.objects.Team;

@Syntax({"[the] egg[ ]wars team [(from|of)] %player%", "%player%'s egg[ ]war[s] team"})
@Config("PluginHooks.Eggwars")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
@RegisterSimpleEnum(ExprClass=Team.class, value="ewteam")
public class ExprEggwarsPlayerTeam extends SimpleExpression<Team>{
	
	private Expression<EwPlayer> player;
	@Override
	public Class<? extends Team> getReturnType() {
		return Team.class;
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
		return "%player%'s egg[ ]war[s] team";
	}
	@Override
	@Nullable
	protected Team[] get(Event e) {
		if (player != null) {
			return new Team[]{player.getSingle(e).getTeam()};
		}
		return null;
	}
}