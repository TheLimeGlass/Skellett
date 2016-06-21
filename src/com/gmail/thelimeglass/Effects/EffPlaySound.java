package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.Event;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffPlaySound extends Effect {
	
	//[skellett] play sound %string% at [loc|location] %location% [with|at|and] volume %number% [and|with|at] pitch %number%
	
	private Expression<String> sound;
	private Expression<Location> location;
	private Expression<Number> volume;
	private Expression<Number> pitch;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		sound = (Expression<String>) e[0];
		location = (Expression<Location>) e[1];
		volume = (Expression<Number>) e[2];
		pitch = (Expression<Number>) e[3];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] play sound %string% at [loc|location] %location% [with|at|and] volume %number% [and|with|at] pitch %number%";
	}
	@Override
	protected void execute(Event e) {
		Sound s = Sound.valueOf(sound.getSingle(e).replace("\"", "").trim().replace(" ", "_").toUpperCase());
		try {
			Sound.valueOf(s.toString().replace("\"", "").trim().replace(" ", "_").toUpperCase());
		} catch (IllegalArgumentException t) {
			Skript.error(s.toString() + " Unknown sound type");
			return;
		}
		for (Location world : location.getAll(e)) {
			world.getWorld().playSound(location.getSingle(e), s, volume.getSingle(e).longValue(), pitch.getSingle(e).longValue());
		}
	}
}