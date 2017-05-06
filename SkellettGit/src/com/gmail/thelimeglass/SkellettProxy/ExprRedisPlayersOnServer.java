package com.gmail.thelimeglass.SkellettProxy;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettPacket;
import com.gmail.thelimeglass.SkellettPacketType;
import com.gmail.thelimeglass.Sockets;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[(the|all)] [of] [the] redis[[ ]bungee] players on server %string%", "redis[[ ]bungee] server %string%'s players"})
@Config("PluginHooks.RedisBungee")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprRedisPlayersOnServer extends SimpleExpression<String>{
	
	private Expression<String> server;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		server = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(the|all)] [of] [the] redis[[ ]bungee] players on server %string%";
	}
	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	protected String[] get(Event e) {
		Object socket = Sockets.send(new SkellettPacket(true, server.getSingle(e), SkellettPacketType.REDISSERVERPLAYERS));
		if (socket != null) {
			ArrayList<String> data = new ArrayList<String>();
			for (UUID uuid : (Set<UUID>) socket) {
				data.add(uuid.toString());
			}
			return data.toArray(new String[data.size()]);
		}
		return null;
	}
}