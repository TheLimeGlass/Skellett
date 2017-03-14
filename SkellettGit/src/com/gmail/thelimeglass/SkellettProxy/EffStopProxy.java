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

@Syntax("[(skellett[ ][(cord|proxy)]|bungee[ ][cord])] (stop|kill|end) [bungee[[ ][cord]] proxy [[with] (message|msg|string|text) %-string%]")
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
public class EffStopProxy extends Effect {
	
	private Expression<String> message;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		message = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[(skellett[ ][(cord|proxy)]|bungee[ ][cord])] (stop|kill|end) [bungee[[ ][cord]] proxy [[with] (message|msg|string|text)] %string%";
	}
	@Override
	protected void execute(Event e) {
		String msg = null;
		if (message != null) {
			msg = message.getSingle(e);
		}
		Sockets.send(new SkellettPacket(false, msg, SkellettPacketType.PROXYSTOP));
	}
}
