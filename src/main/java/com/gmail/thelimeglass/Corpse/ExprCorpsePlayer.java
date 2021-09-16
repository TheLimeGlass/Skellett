package com.gmail.thelimeglass.Corpse;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.golde.bukkit.corpsereborn.nms.Corpses.CorpseData;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[the] name of corpse %corpse%", "corpse %corpse%'s name"})
@Config("PluginHooks.CorpseReborn")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprCorpsePlayer extends SimpleExpression<String> {
	
	private Expression<CorpseData> corpse;

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
		corpse = (Expression<CorpseData>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] player of corpse %corpse%";
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		if (corpse != null) {
			return new String[]{corpse.getSingle(e).getCorpseName()};
		}
		return null;
	}

}