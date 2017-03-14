package com.gmail.thelimeglass.Corpse;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.golde.bukkit.corpsereborn.nms.Corpses.CorpseData;

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

@Syntax("[(the|all)] [of] [the] corpse[s]")
@Config("PluginHooks.CorpseReborn")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.SIMPLE)
public class ExprCorpseAll extends SimpleExpression<CorpseData>{
	
	@Override
	public Class<? extends CorpseData> getReturnType() {
		return CorpseData.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(the|all)] [of] [the] corpse[s]";
	}
	@Override
	@Nullable
	protected CorpseData[] get(Event e) {
		return CorpseManager.getAll();
	}
}