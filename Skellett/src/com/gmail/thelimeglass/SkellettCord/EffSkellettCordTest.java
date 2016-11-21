package com.gmail.thelimeglass.SkellettCord;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketCustom;

public class EffSkellettCordTest extends Effect {
	
	//skellettcord test
	
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "skellettcord test";
	}
	@Override
	protected void execute(Event e) {
		PacketCustom packet = new PacketCustom("SkellettCord", "Ping");
        String callback = (String) packet.send();
        Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + callback));
	}
}