package com.gmail.thelimeglass.Npcs;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("(delete|unregister|remove) (the|all) [of] [the] (npcs|citizens)")
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
public class EffNpcDeleteAll extends Effect {
	
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(delete|unregister|remove) [(the|all)] [of] [the] (npcs|citizens)";
	}
	@Override
	protected void execute(Event e) {
		NPCRegistry registry = CitizensAPI.getNPCRegistry();
		registry.deregisterAll();
	}
}
