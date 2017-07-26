package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondIsWhitelisted extends Condition {
	
	//[server] whitelist[ed] [state]
	
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[server] whitelist[ed] [state]";
	}
	public boolean check(Event e) {
		try {
			if (Bukkit.hasWhitelist()) {
				return true;
			} else {
				return false;					
			}
		}
		catch (Exception e1) {
			return false;
		}
	}
}