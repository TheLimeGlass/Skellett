package com.gmail.thelimeglass.OITB;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.wazup.oitb.OneInTheBattle;
import me.wazup.oitb.OneInTheBattleAPI;
import me.wazup.oitb.PlayerData;

public class EffOITBSetCooldown extends Effect {

	//[OITB] [set] %player% cool[ ]down of [ability] %string% to %integer%
	
	private Expression<Player> player;
	private Expression<String> ability;
	private Expression<Integer> time;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		ability = (Expression<String>) e[1];
		time = (Expression<Integer>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[OITB] [set] %player% cool[ ]down of [ability] %string% to %integer%";
	}
	@Override
	protected void execute(Event e) {
		OneInTheBattleAPI api = OneInTheBattle.api;
		PlayerData stats = api.getPlayerData(player.getSingle(e));
		stats.setCooldown(ability.getSingle(e), time.getSingle(e));
	}
}