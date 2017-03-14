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

@Syntax({"[[(skellett[ ][(cord|proxy)]|bungee[ ][cord])]] kick [(the|all)] [of] [the] bungee[ ][cord] players [(by reason of|because [of]|on account of|due to) %-string%]", "[(skellett[ ][(cord|proxy)]|bungee[ ][cord])] kick [(the|all)] [of] [the] players from bungee[ ][cord] [(by reason of|because [of]|on account of|due to) %-string%]"})
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
public class EffBungeeKickAllPlayers extends Effect {

	private Expression<String> reason;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		reason = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[(skellett[ ][(cord|proxy)]|bungee[ ][cord])] kick [all] bungee[ ][cord] players [(by reason of|because [of]|on account of|due to)] %string%";
	}
	@Override
	protected void execute(Event e) {
		String msg = "Kicked from the bungeecord";
		if (reason != null) {
			msg = reason.getSingle(e);
		}
		Sockets.send(new SkellettPacket(false, msg, SkellettPacketType.KICKPLAYERS));
	}
}
