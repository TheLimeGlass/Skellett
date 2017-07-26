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
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[the] last login of redis[[ ]bungee] [(player|uuid)] %string%", "redis[[ ]bungee] [(player|uuid)] %string%'s last login"})
@Config("PluginHooks.RedisBungee")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprRedisPlayerLastLogin extends SimpleExpression<Number>{
	
	private Expression<String> player;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] last login of redis[[ ]bungee] [(player|uuid)] %string%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (!(player.getSingle(e) instanceof String)) {
			if (Skellett.instance.getConfig().getBoolean("debug")) {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cSkellettProxy: Type must be String not " + player.getSingle(e)));
			}
			return null;
		}
		UUID uniqueId = null;
		try {
			uniqueId = UUID.fromString(player.getSingle(e));
		} catch (IllegalArgumentException ex) {}
		Number longDate = null;
		if (uniqueId != null) {
			longDate = (Number) Sockets.send(new SkellettPacket(true, uniqueId, SkellettPacketType.REDISLASTLOGIN));
		} else {
			longDate = (Number) Sockets.send(new SkellettPacket(true, player.getSingle(e), SkellettPacketType.REDISLASTLOGIN));
		}
		if (longDate != null) {
			return new Number[]{longDate};
		}
		return null;
	}
}