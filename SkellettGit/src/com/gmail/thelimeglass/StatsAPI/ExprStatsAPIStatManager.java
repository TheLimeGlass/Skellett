package com.gmail.thelimeglass.StatsAPI;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterSimpleEnum;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import nl.lolmewn.stats.api.user.StatsHolder;
import nl.lolmewn.stats.bukkit.api.event.StatsHolderUpdateEvent;

@Syntax("[the] [skellett] event stats[ ]api stat manager")
@Config("PluginHooks.StatsAPI")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.SIMPLE)
@RegisterSimpleEnum(ExprClass=StatsHolder.class, value="statsholder")
public class ExprStatsAPIStatManager extends SimpleExpression<StatsHolder> {
	
	public Class<? extends StatsHolder> getReturnType() {
		return StatsHolder.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(StatsHolderUpdateEvent.class)) {
			Skript.error("You can not use this horrible stats api thing expression in any event but StatsHolderUpdateEvent!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "StatsHolderUpdateEvent thing";
	}
	@Nullable
	protected StatsHolder[] get(Event e) {
		return new StatsHolder[]{((StatsHolderUpdateEvent)e).getHolder()};
	}
}