package com.gmail.thelimeglass.Holograms;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import de.inventivegames.hologram.Hologram;
import de.inventivegames.hologram.HologramAPI;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;

public class EffNewHologram extends Effect {

	//[skellett] (create|spawn|summon|place) [a] holo[gram] [(named|with)] [text] %string% at %location% [[with] id] %string%
	
	private Expression<String> name, ID;
	private Expression<Location> loc;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		name = (Expression<String>) e[0];
		loc = (Expression<Location>) e[1];
		ID = (Expression<String>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (create|spawn|summon|place) [a] holo[gram] [(named|with)] [text] %string% at %location% [[with] id] %string%";
	}
	@Override
	protected void execute(Event e) {
		if (HologramManager.contains(ID.getSingle(e))) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cThere is already a hologram with the id " + ID.getSingle(e)));
			return;
		}
		Hologram holo = HologramAPI.createHologram(loc.getSingle(e), name.getSingle(e));
		holo.spawn();
		HologramManager.add(ID.getSingle(e), holo);
	}
}
