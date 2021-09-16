package com.gmail.thelimeglass.Npcs;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.npc.NPC;

@Syntax("[the] despawn (npc|citizen)")
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
public class ExprDespawnNpc extends SimpleExpression<NPC>{
	
	@Override
	public Class<? extends NPC> getReturnType() {
		return NPC.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(NPCDespawnEvent.class)) {
			Skript.error("You can not use despawned npc expression in any event but on npc despawn!");
			return false;
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] despawn (npc|citizen)";
	}
	@Override
	@Nullable
	protected NPC[] get(Event e) {
		return new NPC[]{((NPCDespawnEvent)e).getNPC()};
	}
}