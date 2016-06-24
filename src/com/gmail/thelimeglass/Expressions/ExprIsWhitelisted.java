package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprIsWhitelisted extends SimpleExpression<Boolean>{
	
	//[get] [server] whitelist[ed] [state]
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "hasWhitelist()";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		return new Boolean[]{Bukkit.hasWhitelist()};
	}
}