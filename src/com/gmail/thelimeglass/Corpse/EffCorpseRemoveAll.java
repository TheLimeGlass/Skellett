package com.gmail.thelimeglass.Corpse;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

@Syntax("(delete|unregister|remove) (the|all) [of] [the] corpse[s]")
@Config("PluginHooks.CorpseReborn")
@FullConfig
@MainConfig
public class EffCorpseRemoveAll extends Effect {
	
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(delete|unregister|remove) (the|all) [of] [the] corpse[s]";
	}
	@Override
	protected void execute(Event e) {
		CorpseManager.unregisterAll();
	}
}
