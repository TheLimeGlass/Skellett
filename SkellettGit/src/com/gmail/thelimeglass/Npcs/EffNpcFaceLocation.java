package com.gmail.thelimeglass.Npcs;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("[(make|change)] (npc|citizen) %npc%['s] look[ing] (at|in|towards) [direction [of]] [location] %location%")
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
public class EffNpcFaceLocation extends Effect {
	
	private Expression<NPC> npc;
	private Expression<Location> location;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		npc = (Expression<NPC>) e[0];
		location = (Expression<Location>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[(make|change)] (npc|citizen) %npc%['s] look[ing] (at|in|towards) [direction [of]] [location] %location%";
	}
	@Override
	protected void execute(Event e) {
		npc.getSingle(e).faceLocation(location.getSingle(e));
	}
}
