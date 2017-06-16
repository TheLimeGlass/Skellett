package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.clip.placeholderapi.PlaceholderAPI;

@Syntax("[parse[d]] place[ ]holder [(from|with)] %string% [[(from|for)] %-player%]")
@Config("PluginHooks.PlaceholderAPI")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprPlaceholderAPI extends SimpleExpression<String> {
	
	private Expression<String> input;
	private Expression<Player> player;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		input = (Expression<String>) e[0];
		player = (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[parse[d]] place[ ]holder [(from|with)] %string% [[(from|for)] %-player%]";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		if (player != null) {
			return new String[] {PlaceholderAPI.setPlaceholders(player.getSingle(e), input.getSingle(e))};
		}
		return new String[] {PlaceholderAPI.setPlaceholders(null, input.getSingle(e))};
	}
}