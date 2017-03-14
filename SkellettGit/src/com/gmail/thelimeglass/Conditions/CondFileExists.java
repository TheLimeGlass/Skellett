package com.gmail.thelimeglass.Conditions;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondFileExists extends Condition {

	//[skellett] [file] exist(s|ance) [(at|of)] %string% [is %-boolean%]
	//file %string% exists
	
	private Expression<String> file;
	private Expression<Boolean> boo;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		file = (Expression<String>) e[0];
		boo = (Expression<Boolean>) e[1];
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [file] exist(s|ance) [(at|of)] %string% [is %-boolean%]";
	}
	public boolean check(Event e) {
		Boolean bool = true;
		if (boo != null) {
			bool = boo.getSingle(e);
		}
		try {
			File f = new File(file.getSingle(e));
	        if (f.exists()) {
    			if (bool == true) {
    				return true;
    			} else {
    				return false;
    			}
    		} else {
    			if (bool == false) {
    				return true;
    			} else {
    				return false;
    			}
    		}
		}
		catch (Exception e1) {
			return false;
		}
	}
}