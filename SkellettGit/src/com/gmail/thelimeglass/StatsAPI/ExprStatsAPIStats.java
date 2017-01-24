package com.gmail.thelimeglass.StatsAPI;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import nl.lolmewn.stats.api.stat.Stat;
import nl.lolmewn.stats.api.user.StatsHolder;

@Syntax("[(the|all)] [of] [the] stats[ ]api stats (of|from) [stat[s] holder] %statsholder%")
@Config("PluginHooks.StatsAPI")
@FullConfig
@MainConfig
@PropertyType("COMBINED")
public class ExprStatsAPIStats extends SimpleExpression<Stat>{
	
	private Expression<StatsHolder> holder;
	@Override
	public Class<? extends Stat> getReturnType() {
		return Stat.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		holder = (Expression<StatsHolder>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(the|all)] [of] [the] stats[ ]api stats (of|from) [stat[s] holder] %statsholder%";
	}
	@Override
	@Nullable
	protected Stat[] get(Event e) {
		return holder.getSingle(e).getStats().toArray(new Stat[holder.getSingle(e).getStats().size()]);
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.REMOVE) {
			holder.getSingle(e).removeStat((Stat)delta[0]);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Stat.class);
		}
		return null;
	}
}