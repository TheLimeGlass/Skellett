package com.gmail.thelimeglass.SkellettProxy;

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

@Syntax("[the] [(skellett[ ][(cord|proxy)]|bungee[ ][cord])] online [stat(us|e)] of (skellett[ ][(cord|proxy)]|bungee[ ][cord]) server %string%")
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
@PropertyType(ExpressionType.COMBINED)
public class ExprBungeeServerOnline extends SimpleExpression<Boolean>{
	
	private Expression<String> server;
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		server = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] [(skellett[ ][(cord|proxy)]|bungee[ ][cord])] online [stat(us|e)] of (skellett[ ][(cord|proxy)]|bungee[ ][cord]) server %string%";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		Boolean online = (Boolean) Sockets.send(new SkellettPacket(true, server.getSingle(e), SkellettPacketType.ISSERVERONLINE));
		if (online != null) {
			return new Boolean[]{online};
		}
		return null;
	}
}