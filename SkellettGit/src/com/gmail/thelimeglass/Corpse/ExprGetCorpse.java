package com.gmail.thelimeglass.Corpse;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.golde.bukkit.corpsereborn.nms.Corpses.CorpseData;

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

@Syntax("[the] corpse with ID %string%")
@Config("PluginHooks.CorpseReborn")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
@RegisterSimpleEnum(ExprClass=CorpseData.class, value="corpse")
public class ExprGetCorpse extends SimpleExpression<CorpseData>{
	
	private Expression<String> ID;
	@Override
	public Class<? extends CorpseData> getReturnType() {
		return CorpseData.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		ID = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] corpse with ID %string%";
	}
	@Override
	@Nullable
	protected CorpseData[] get(Event e) {
		if (CorpseManager.contains(ID.getSingle(e)) && ID != null) {
			return new CorpseData[]{CorpseManager.get(ID.getSingle(e))};
		}
		return null;
	}
}