package com.gmail.thelimeglass.Holograms;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] (delete|remove|clear) [(the|all)] [of] [the] holograms")
@Config("PluginHooks.Holograms")
@FullConfig
@MainConfig
public class EffHologramClearAll extends Effect {
	
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (delete|remove|clear) [(the|all)] [of] [the] holograms";
	}
	@Override
	protected void execute(Event e) {
		Skellett.hologramManager.clear();
	}
}