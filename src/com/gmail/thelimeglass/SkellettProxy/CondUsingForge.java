package com.gmail.thelimeglass.SkellettProxy;

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

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[(skellett[ ][(cord|proxy)]|bungee[ ][cord])] [(player|uuid)] %string% (1�(has|is)|2�(is(n't| not))) [(running|using)] [the] forge [client]")
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
public class CondUsingForge extends Condition {
	
	private Expression<String> player;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<String>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(skellett[ ][(cord|proxy)]|bungee[ ][cord])] [(player|uuid)] %string% (1�(has|is)|2�(is(n't| not))) [using] [the] forge [client]";
	}
	public boolean check(Event e) {
		if (!(player.getSingle(e) instanceof String)) {
			if (Skellett.instance.getConfig().getBoolean("debug")) {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cSkellettProxy: Type must be String not " + player.getSingle(e)));
			}
			return false;
		}
		UUID uniqueId = null;
		try {
			uniqueId = UUID.fromString(player.getSingle(e));
		} catch (IllegalArgumentException ex) {}
		Boolean data = null;
		if (uniqueId != null) {
			data = (Boolean) Sockets.send(new SkellettPacket(true, uniqueId, SkellettPacketType.ISUSINGFORGE));
		} else {
			data = (Boolean) Sockets.send(new SkellettPacket(true, player.getSingle(e), SkellettPacketType.ISUSINGFORGE));
		}
		if (data == null) {
			return false;
		}
		if (data) {
			return isNegated();
		} else {
			return !isNegated();
		}
	}
}