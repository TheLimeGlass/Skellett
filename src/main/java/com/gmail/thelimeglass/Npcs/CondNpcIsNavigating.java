package com.gmail.thelimeglass.Npcs;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.ai.Navigator;
import net.citizensnpcs.api.npc.NPC;

@Syntax("[the] (npc|citizen) %citizen% (1¦is|2¦is(n't| not)) (navigating|moving)")
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
public class CondNpcIsNavigating extends Condition {
	
	private Expression<NPC> npc;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		npc = (Expression<NPC>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] (npc|citizen) %npc% (1¦is|2¦is(n't| not)) (navigating|moving)";
	}
	public boolean check(Event e) {
		Navigator navigator = npc.getSingle(e).getNavigator();
		return navigator.isNavigating() ? isNegated() : !isNegated();
	}
}