package com.gmail.thelimeglass.OITB;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.wazup.oitb.OneInTheBattle;
import me.wazup.oitb.OneInTheBattleAPI;
import me.wazup.oitb.PlayerData;

public class CondOITBHasCooldown extends Condition {
	
	//[OITB] %player% has cool[ ]down [on] [ability] %string%
	
	private Expression<Player> player;
	private Expression<String> ability;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		ability = (Expression<String>) e[1];
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[OITB] %player% has cool[ ]down [on] [ability] %string%";
	}
	public boolean check(Event e) {
        try {
        	OneInTheBattleAPI api = OneInTheBattle.api;
    		PlayerData stats = api.getPlayerData(player.getSingle(e));
    		stats.hasCooldown(player.getSingle(e), ability.getSingle(e));
            return true;
        }
        catch (Exception e1) {
            return false;
        }
    }
}