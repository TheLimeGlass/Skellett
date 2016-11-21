package com.gmail.thelimeglass.SkellettCord;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketCustom;

public class ExprPlayerPing extends SimpleExpression<Number>{
	
	//%player%'s bungee[[ ]cord] ping
	//bungee[[ ]cord] ping of %player%
	
	private Expression<Player> player;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "bungee[[ ]cord] ping of %player%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		String data = "PlayerPing " + player.getSingle(e).getName().replace(" ", "");
		PacketCustom packet = new PacketCustom("SkellettCord", data);
        Integer num = (Integer) packet.send();
        if (num != null && !num.equals("null")) {
        	return new Number[]{num};
        }
		return null;
	}
}