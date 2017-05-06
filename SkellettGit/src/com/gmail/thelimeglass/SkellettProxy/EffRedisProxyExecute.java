package com.gmail.thelimeglass.SkellettProxy;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettPacket;
import com.gmail.thelimeglass.SkellettPacketType;
import com.gmail.thelimeglass.Sockets;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.SkellettProxy;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("(run|execute) redis[[ ]bungee] [proxy] command %string% on [all [of the]] proxy server[s] [[with] id %-sting%]")
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
public class EffRedisProxyExecute extends Effect {
	
	private Expression<String> id, command;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		command = (Expression<String>) e[0];
		id = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(run|execute) redis[[ ]bungee] [proxy] command %string% on [all [of the]] proxy server[s] [[with] id %-sting%]";
	}
	@Override
	protected void execute(Event e) {
		if (command != null) {
			if (id != null) {
				Sockets.send(new SkellettPacket(false, command.getSingle(e), id.getSingle(e), SkellettPacketType.REDISPROXYCOMMAND));
			} else {
				Sockets.send(new SkellettPacket(false, command.getSingle(e), SkellettPacketType.REDISPROXYCOMMAND));
			}
		}
	}
}
