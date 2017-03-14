package com.gmail.thelimeglass.Npcs;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.RegisterEnum;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.NPCDespawnEvent;

@Syntax("[the] [(npc|citizen)] despawn reason")
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
@RegisterEnum("despawnreason")
public class ExprDespawnReason extends SimpleExpression<DespawnReason>{
	
	@Override
	public Class<? extends DespawnReason> getReturnType() {
		return DespawnReason.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(NPCDespawnEvent.class)) {
			Skript.error("You can not use npc despawn reason expression in any event but on npc despawn!");
			return false;
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] [(npc|citizen)] despawn reason";
	}
	@Override
	@Nullable
	protected DespawnReason[] get(Event e) {
		return new DespawnReason[]{((NPCDespawnEvent)e).getReason()};
	}
}