package com.gmail.thelimeglass.OITB;

import org.bukkit.entity.Player;

import me.wazup.oitb.OneInTheBattle;
import me.wazup.oitb.OneInTheBattleAPI;
import me.wazup.oitb.PlayerData;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprOITBGetPlayTime extends SimpleExpression<String>{
	
	//[OITB] [get] play[er][ ]time of %player%
	
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
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[OITB] [get] play[er][ ]time of %player%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		OneInTheBattleAPI api = OneInTheBattle.api;
		PlayerData stats = api.getPlayerData(player.getSingle(e));
		return new String[]{stats.getPlaytime()};
	}
}