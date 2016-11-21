package com.gmail.thelimeglass.Holograms;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import de.inventivegames.hologram.HologramAPI;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

public class EffDeleteHologram extends Effect {

	//(delete|remove|despawn|clear|kill) holo[gram] [with] id %string%
	
	private Expression<String> ID;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		ID = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(delete|remove|despawn|clear|kill) holo[gram] [with] id %string%";
	}
	@Override
	protected void execute(Event e) {
		if (HologramManager.contains(ID.getSingle(e))) {
			HologramAPI.removeHologram(HologramManager.get(ID.getSingle(e)));
			HologramManager.remove(ID.getSingle(e));
		}
	}
}
