package com.gmail.thelimeglass.SkellettProxy;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettPacket;
import com.gmail.thelimeglass.SkellettPacketType;
import com.gmail.thelimeglass.Sockets;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.SkellettProxy;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("eval[uate] [skript] [code] %string% (on|from) [the] [bungee[ ][cord]] [server] %string%")
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
public class EffBungeeEvaluate extends Effect {
	
	private Expression<String> server, code;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		code = (Expression<String>) e[0];
		server = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "eval[uate] [skript] [code] %string% (on|from) [the] [bungee[ ][cord]] [server] %string%";
	}
	@Override
	protected void execute(Event e) {
		ArrayList<Object> data = new ArrayList<Object>(Arrays.asList(server.getSingle(e), code.getSingle(e)));
		Sockets.send(new SkellettPacket(false, data, SkellettPacketType.EVALUATE));
	}
}
