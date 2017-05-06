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

@Syntax("[(skellett[ ][(cord|proxy)]|bungee[ ][cord])]['s] player limit")
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
@PropertyType(ExpressionType.SIMPLE)
public class ExprPlayerLimit extends SimpleExpression<Number>{
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(skellett[ ][(cord|proxy)]|bungee[ ][cord])]['s] player limit";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		Number limit = (Number) Sockets.send(new SkellettPacket(true, null, SkellettPacketType.BUNGEEPLAYERLIMIT));
		if (limit != null) {
			return new Number[]{limit};
		}
		return null;
	}
}