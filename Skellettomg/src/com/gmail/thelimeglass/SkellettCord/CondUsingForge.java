package com.gmail.thelimeglass.SkellettCord;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketCustom;

public class CondUsingForge extends Condition {
	
	//%player% (1¦(has|is)|2¦(is(n't| not))) [using] [the] forge [client]
	
	private Expression<Player> player;
	Boolean boo = true;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<Player>) e[0];
		if (parser.mark == 2) {boo = false;}
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "%player% (1¦(has|is)|2¦(is(n't| not))) [using] [the] forge [client]";
	}
	public boolean check(Event e) {
		String data = "Forge " + player.getSingle(e).getName().replace(" ", "");
		PacketCustom packet = new PacketCustom("SkellettCord", data);
		Boolean check = (Boolean) packet.send();
        if (check != null && !check.equals("null")) {
        	if (check) {
    			if (boo == true) {
    				return true;
    			} else {
    				return false;
    			}
    		} else {
    			if (boo == false) {
    				return true;
    			} else {
    				return false;
    			}
    		}
        }
		return false;
	}
}