package com.gmail.thelimeglass.SkellettCord;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketCustom;

public class ExprBungeecordVersion extends SimpleExpression<String>{
	
	//bungee[[ ]cord] version
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
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
	protected String[] get(Event e) {
		PacketCustom packet = new PacketCustom("SkellettCord", "Bungeecord Version");
        String version = (String) packet.send();
        if (version != null) {
        	return new String[]{version};
        }
		return null;
	}
}