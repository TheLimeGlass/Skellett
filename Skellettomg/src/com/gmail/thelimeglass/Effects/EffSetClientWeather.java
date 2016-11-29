package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;


import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffSetClientWeather extends Effect {
	
	//[set] [client] weather of [player] %player% to %string%
	
	private Expression<Player> player;
	private Expression<String> w;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		w = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[set] [client] weather of [player] %player% to %string%";
	}
	@Override
	protected void execute(Event e) {
		WeatherType weather = WeatherType.valueOf(w.getSingle(e).replace("\"", "").trim().replace(" ", "_").toUpperCase());
		try {
			WeatherType.valueOf(weather.toString().replace("\"", "").trim().replace(" ", "_").toUpperCase());
		} catch (IllegalArgumentException t) {
			Skript.error(weather.toString() + " Is an unknown weather type. Use DOWNFALL or CLEAR");
			return;
		}
		player.getSingle(e).setPlayerWeather((WeatherType)weather);
	}
}