package com.gmail.thelimeglass.Npcs;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;

@Syntax("[entity] %entity% (1¦is|2¦is(n't| not)) [a[n]] (npc|citizen)")
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
public class CondEntityIsNpc extends Condition {
	
	private Expression<Entity> entity;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entity = (Expression<Entity>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "(npc|citizen) %npc% (1¦is|2¦is(n't| not)) spawned";
	}
	public boolean check(Event e) {
		NPCRegistry registry = CitizensAPI.getNPCRegistry();
		if (registry.isNPC(entity.getSingle(e))) {
			return isNegated();
		} else {
			return !isNegated();
		}
	}
}