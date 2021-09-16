package com.gmail.thelimeglass.Corpse;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.golde.bukkit.corpsereborn.nms.Corpses.CorpseData;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("(delete|remove|unregister) corpse %corpse%")
@Config("PluginHooks.CorpseReborn")
@FullConfig
@MainConfig
public class EffCorpseRemove extends Effect {
	
	private Expression<CorpseData> corpse;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		corpse = (Expression<CorpseData>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(delete|remove|unregister) corpse %corpse%";
	}
	@Override
	protected void execute(Event e) {
		if (corpse != null) {
			CorpseManager.removeCorpse(corpse.getSingle(e));
		}
	}
}
