package com.gmail.thelimeglass.Npcs;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;

@Syntax("(make|force) [the] (npc|citizen) %citizen% [to] (target|attack|damage|follow) %entity% [[with] [aggressive [state]] %-boolean%]")
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
public class EffNpcAttack extends Effect {
	
	private Expression<NPC> npc;
	private Expression<Entity> entity;
	private Expression<Boolean> aggressive;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		npc = (Expression<NPC>) e[0];
		entity = (Expression<Entity>) e[1];
		aggressive = (Expression<Boolean>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(make|force) [the] (npc|citizen) %npc% [to] (target|attack|damage|follow) %entity% [[with] [aggressive [state]] %-boolean%]";
	}
	@Override
	protected void execute(Event e) {
		if (npc != null && entity != null) {
			Boolean test = aggressive.getSingle(e);
			if (aggressive == null) {
				test = true;
			}
			npc.getSingle(e).getNavigator().setTarget(entity.getSingle(e), test);
		}
	}
}
