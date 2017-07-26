package com.gmail.thelimeglass.SkellettProxy;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.SkellettPacket;
import com.gmail.thelimeglass.SkellettPacketType;
import com.gmail.thelimeglass.Sockets;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.SkellettProxy;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

@Syntax("[(skellett[ ][(cord|proxy)]|bungee[ ][cord])] (send|display|show) action[ ]bar [with [text]] %string% to bungee[ ][cord] [(player|uuid)] %string%")
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
public class EffBungeeActionbar extends Effect {

	private Expression<String> message;
	private Expression<String> player;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		message = (Expression<String>) e[0];
		player = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[(skellett[ ][(cord|proxy)]|bungee[ ][cord])] (send|display|show) action[ ]bar [with [text]] %string% to bungee[ ][cord] [(player|uuid)] %string%";
	}
	@Override
	protected void execute(Event e) {
		if (!(player.getSingle(e) instanceof String)) {
			if (Skellett.instance.getConfig().getBoolean("debug")) {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cSkellettProxy: Type must be String not " + player.getSingle(e)));
			}
			return;
		}
		UUID uniqueId = null;
		try {
			uniqueId = UUID.fromString(player.getSingle(e));
		} catch (IllegalArgumentException ex) {}
		if (uniqueId != null) {
			ArrayList<Object> data = new ArrayList<Object>(Arrays.asList(uniqueId, message.getSingle(e)));
			Sockets.send(new SkellettPacket(false, data, SkellettPacketType.ACTIONBAR));
		} else {
			ArrayList<Object> data = new ArrayList<Object>(Arrays.asList(uniqueId, message.getSingle(e)));
			Sockets.send(new SkellettPacket(false, data, SkellettPacketType.ACTIONBAR));
		}
	}
}
