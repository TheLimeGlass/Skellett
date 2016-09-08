package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.event.Event;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffSpawnParticle extends Effect {
	
	//[skellett] (create|place|spawn|play|make|summon) particle [effect] %string% at %location% [[with] [offset] %number%[(,| and)] %number%[(,| and)] %number%] at speed %number% and amount %integer%
	
	private Expression<String> effect;
	private Expression<Location> location;
	private Expression<Number> off1;
	private Expression<Number> off2;
	private Expression<Number> off3;
	private Expression<Number> speed;
	private Expression<Integer> amount;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		effect = (Expression<String>) e[0];
		location = (Expression<Location>) e[1];
		off1 = (Expression<Number>) e[2];
		off2 = (Expression<Number>) e[3];
		off3 = (Expression<Number>) e[4];
		speed = (Expression<Number>) e[5];
		amount = (Expression<Integer>) e[6];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (create|place|spawn|play|make|summon) particle [effect] %string% at %location% [[with] [offset] %number%[(,| and)] %number%[(,| and)] %number%] at speed %number% and amount %integer%";
	}
	@Override
	protected void execute(Event e) {
		org.bukkit.Effect particle = org.bukkit.Effect.valueOf(effect.getSingle(e).replace("\"", "").trim().replace(" ", "_").toUpperCase());
		try {
			particle = org.bukkit.Effect.valueOf(particle.toString().replace("\"", "").trim().replace(" ", "_").toUpperCase());
		} catch (IllegalArgumentException t) {
			Skript.error(particle.toString() + " Unknown particle type" + particle);
			return;
		}
		if (off1 != null || off2 != null || off3 != null) {
			sendEffect(particle, location.getSingle(e), off1.getSingle(e).floatValue(), off2.getSingle(e).floatValue(), off3.getSingle(e).floatValue(), speed.getSingle(e).floatValue(), amount.getSingle(e));
		} else {
			sendEffect(particle, location.getSingle(e), speed.getSingle(e).floatValue(), amount.getSingle(e));
		}
	}
	@SuppressWarnings("unused")
	private void sendColor(org.bukkit.Effect particle, Location l, int data, Color color) {
		l.getWorld().spigot().playEffect(l, particle, data, 0, (float)gc(color.getRed()), (float)gc(color.getGreen()), (float)gc(color.getBlue()), 1, 0, 35);
	}

	@SuppressWarnings("unused")
	private void sendColor(org.bukkit.Effect particle, Location l, int data, java.awt.Color color) {
		l.getWorld().spigot().playEffect(l, particle, data, 0, (float)gc(color.getRed()), (float)gc(color.getGreen()), (float)gc(color.getBlue()), 1, 0, 35);
	}

	private void sendEffect(org.bukkit.Effect particle, Location l, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
		l.getWorld().spigot().playEffect(l, particle, 0, 0, offsetX, offsetY, offsetZ, speed, amount, 35);
	}
	private void sendEffect(org.bukkit.Effect particle, Location l, float speed, int amount) {
		l.getWorld().spigot().playEffect(l, particle, 0, 0, 0, 0, 0, speed, amount, 35);
	}

	private static double gc(double value) {
		if (value <= 0) {
			value = -1;
		}
		return value / 255;
	}
}