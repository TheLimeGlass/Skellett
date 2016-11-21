package com.gmail.thelimeglass.SkellettCord;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketCustom;

public class ExprPlayerLimit extends SimpleExpression<Number>{
	
	//bungee[[ ]cord] player limit
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "bungee[[ ]cord] version";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		PacketCustom packet = new PacketCustom("SkellettCord", "Player Limit");
        Integer limit = (Integer) packet.send();
        if (limit != null) {
        	return new Number[]{limit};
        }
		return null;
	}
}