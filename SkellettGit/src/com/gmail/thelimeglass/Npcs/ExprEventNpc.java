package com.gmail.thelimeglass.Npcs;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.event.NPCEvent;
import net.citizensnpcs.api.npc.NPC;

@Syntax("[the] [event(-| )](citizen|npc)")
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.SIMPLE)
public class ExprEventNpc extends SimpleExpression<NPC>{
	
	@Override
	public Class<? extends NPC> getReturnType() {
		return NPC.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] [event(-| )](citizen|npc)";
	}
	@Override
	@Nullable
	protected NPC[] get(Event e) {
		if (NPCEvent.class.isAssignableFrom(e.getClass())) {
			if (((NPCEvent)e).getNPC() != null) {
				return new NPC[]{((NPCEvent)e).getNPC()};
			}
		}
		return null;
	}
}