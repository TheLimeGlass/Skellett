package com.gmail.thelimeglass.Holograms;

import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import com.sainttx.holograms.api.Hologram;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(the|all)] [of] [the] [skellett] holo[gram][s] ids")
@Config("PluginHooks.Holograms")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.SIMPLE)
public class ExprHolograms extends SimpleExpression<String>{
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
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
		return "[(the|all)] [of] [the] [skellett] holo[gram][s] ids";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		Map<String, Hologram> holos = Skellett.hologramManager.getActiveHolograms();
		return holos.keySet().toArray(new String[holos.keySet().size()]);
	}
}