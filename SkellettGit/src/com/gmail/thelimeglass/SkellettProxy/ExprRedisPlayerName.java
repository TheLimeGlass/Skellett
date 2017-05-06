package com.gmail.thelimeglass.SkellettProxy;

import java.util.UUID;

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

@Syntax("[the] redis[[ ]bungee] [player] [user[ ]]name of uuid %string% [[with] expensive lookup %-boolean%]")
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
@PropertyType(ExpressionType.COMBINED)
public class ExprRedisPlayerName extends SimpleExpression<String>{
	
	private Expression<String> uuid;
	private Expression<Boolean> lookup;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		uuid = (Expression<String>) e[0];
		lookup = (Expression<Boolean>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] redis[[ ]bungee] [player] [user[ ]]name of uuid %string% [[with] expensive lookup %-boolean%]";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		UUID uniqueId = null;
		try {
			uniqueId = UUID.fromString(uuid.getSingle(e));
		} catch (IllegalArgumentException ex) {}
		if (uniqueId != null) {
			String name = null;
			if (lookup != null) {
				name = (String) Sockets.send(new SkellettPacket(true, uniqueId, SkellettPacketType.REDISPLAYERNAMELOOKUP));
			} else {
				name = (String) Sockets.send(new SkellettPacket(true, uniqueId, SkellettPacketType.REDISPLAYERNAME));
			}
			if (name != null) {
				return new String[]{name};
			}
		}
		return null;
	}
}