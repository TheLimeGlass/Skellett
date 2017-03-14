package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;
import com.squidhq.plugin.APISingleton;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("%player% (1¦is|2¦is(n't| not)) (running|using) [the] [client] SquidHQ [client]")
@Config("PluginHooks.SquidHQ")
@FullConfig
@MainConfig
public class CondSquidHQ extends Condition {
	
	private Expression<Player> player;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<Player>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "%player% (1¦is|2¦is(n't| not)) (running|using) [the] [client] SquidHQ [client]";
	}
	public boolean check(Event e) {
		if (APISingleton.getAPI().isRunning(player.getSingle(e))) {
			return isNegated();
		} else {
			return !isNegated();					
		}
	}
}