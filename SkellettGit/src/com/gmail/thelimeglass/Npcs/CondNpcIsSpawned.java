package com.gmail.thelimeglass.Npcs;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;

@Syntax("(npc|citizen) %npc% (1¦is|2¦is(n't| not)) spawned")
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
public class CondNpcIsSpawned extends Condition {
	
	private Expression<NPC> npc;
	private Boolean boo = true;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		npc = (Expression<NPC>) e[0];
		if (parser.mark == 2) {boo = false;}
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "(npc|citizen) %npc% (1¦is|2¦is(n't| not)) spawned";
	}
	public boolean check(Event e) {
		if (npc.getSingle(e).isSpawned()) {
			if (boo == true) {
				return true;
			} else {
				return false;
			}
		} else {
			if (boo == false) {
				return true;
			} else {
				return false;
			}
		}
	}
}