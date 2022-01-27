package com.gmail.thelimeglass.Npcs;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.Skript;
import ch.njol.skript.conditions.base.PropertyCondition;
import net.citizensnpcs.api.npc.NPC;

@Syntax("(npc|citizen) %citizen% (1¦is|2¦is(n't| not)) spawned")
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
public class CondNpcIsSpawned extends PropertyCondition<NPC> {

	static {
		Skript.registerCondition(CondNpcIsSpawned.class, "(npc|citizen)[s] %citizens% (has|have|is) spawned",
				"(npc|citizen)[s] %citizens% (is|have)(n't| not) spawned");
	}

	@Override
	public boolean check(NPC npc) {
		return npc.isSpawned();
	}

	@Override
	protected String getPropertyName() {
		return "spawned";
	}

}
