package com.gmail.thelimeglass.Conditions;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondFileExists extends Condition {

	//file [exist[(s|ance)] [at]] %string%
	//file %string% exists
	
	private Expression<String> file;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		file = (Expression<String>) e[0];
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "file [exist[(s|ance)] [at]] %string%";
	}
	public boolean check(Event e) {
		try {
			File f = new File(file.getSingle(e));
	        if (f.exists()) {
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