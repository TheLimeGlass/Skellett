package com.gmail.thelimeglass.SkellettProxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettPacket;
import com.gmail.thelimeglass.SkellettPacketType;
import com.gmail.thelimeglass.Sockets;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(the|all)] [of] [the] redis[[ ]bungee] [connected] servers")
@Config("PluginHooks.RedisBungee")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprRedisBungeeServers extends SimpleExpression<String>{
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(the|all)] [of] [the] redis[[ ]bungee] [connected] servers";
	}
	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	protected String[] get(Event e) {
		Object socket = Sockets.send(new SkellettPacket(true, null, SkellettPacketType.REDISSERVERS));
		ArrayList<String> data = new ArrayList<String>();
		for (String server : (List<String>) socket) {
			data.add(server);
		}
		return data.toArray(new String[data.size()]);
	}
}