package com.gmail.thelimeglass.StatsAPI;

import javax.annotation.Nullable;

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
import nl.lolmewn.stats.api.stat.Stat;

@Syntax("[the] stats[ ]api name (of|from) [stat] %stat%")
@Config("PluginHooks.StatsAPI")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprStatsAPIStatName extends SimpleExpression<String>{
	
	private Expression<Stat> stat;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		stat = (Expression<Stat>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] stats[ ]api name (of|from) [stat] %stat%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{stat.getSingle(e).getName()};
	}
}