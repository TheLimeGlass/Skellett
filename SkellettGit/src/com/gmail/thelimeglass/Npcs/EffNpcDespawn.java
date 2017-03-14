package com.gmail.thelimeglass.Npcs;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.npc.NPC;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("(despawn|remove) (npc|citizen) %npc% [with [despawn] reason %-despawnreason%]")
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
public class EffNpcDespawn extends Effect {
	
	private Expression<NPC> npc;
	private Expression<DespawnReason> despawn;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		npc = (Expression<NPC>) e[0];
		despawn = (Expression<DespawnReason>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(despawn|remove) (npc|citizen) %npc% [with [despawn] reason %-despawnreason%]";
	}
	@Override
	protected void execute(Event e) {
		if (despawn != null) {
			npc.getSingle(e).despawn(despawn.getSingle(e));
		} else {
			npc.getSingle(e).despawn();
		}
	}
}
