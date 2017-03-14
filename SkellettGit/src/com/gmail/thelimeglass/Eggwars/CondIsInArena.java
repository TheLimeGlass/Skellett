package com.gmail.thelimeglass.Eggwars;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import es.minetsii.eggwars.objects.EwPlayer;

@Syntax("egg[ ]wars player %ewplayer% (1¦is|2¦is(n't| not)) in a[n] arena")
@Config("PluginHooks.Eggwars")
@FullConfig
@MainConfig
public class CondIsInArena extends Condition {
	
	private Expression<EwPlayer> player;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<EwPlayer>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "egg[ ]wars player %ewplayer% (1¦is|2¦is(n't| not)) in a[n] arena";
	}
	public boolean check(Event e) {
		if (player.getSingle(e).isInArena()) {
			return isNegated();
		} else {
			return !isNegated();
		}
	}
}