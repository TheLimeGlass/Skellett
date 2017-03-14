package com.gmail.thelimeglass.SkellettProxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettPacket;
import com.gmail.thelimeglass.SkellettPacketType;
import com.gmail.thelimeglass.Sockets;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.SkellettProxy;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(the|all)] [of] [the] (skellett[ ][(cord|proxy)]|bungee[ ][cord]) [connected] servers")
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
@PropertyType(ExpressionType.COMBINED)
public class ExprBungeeGetServers extends SimpleExpression<String>{
	
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
		return "[(the|all)] [of] [the] (skellett[ ][(cord|proxy)]|bungee[ ][cord]) [connected] servers";
	}
	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	protected String[] get(Event e) {
		Object socket = Sockets.send(new SkellettPacket(true, null, SkellettPacketType.ALLSERVERS));
		ArrayList<String> data = new ArrayList<String>();
		for (String server : (List<String>) socket) {
			data.add(server);
		}
		return data.toArray(new String[data.size()]);
	}
}