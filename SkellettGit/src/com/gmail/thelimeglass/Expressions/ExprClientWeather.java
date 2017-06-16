package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax("[skellett] [client] weather of %player%")
@Config("ClientWeather")
@PropertyType(ExpressionType.COMBINED)
public class ExprClientWeather extends SimpleExpression<WeatherType>{
	
	private Expression<Player> player;
	@Override
	public Class<? extends WeatherType> getReturnType() {
		return WeatherType.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[client] weather of %player%";
	}
	@Override
	@Nullable
	protected WeatherType[] get(Event e) {
		if (player != null && player.getSingle(e).getPlayerWeather() != null) {
			return new WeatherType[]{player.getSingle(e).getPlayerWeather()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			String w = (String)delta[0];
			WeatherType t = null;
			try {
				t = WeatherType.valueOf(w.replace("\"", "").trim().replace(" ", "_").toUpperCase());
			} catch (IllegalArgumentException error) {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cUnknown weather type " + w));
				return;
			}
			if (t != null) {
				player.getSingle(e).setPlayerWeather(t);
			}
		} else if (mode == ChangeMode.RESET) {
			player.getSingle(e).setPlayerWeather(WeatherType.CLEAR);
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(String.class);
		}
		return null;
	}
}