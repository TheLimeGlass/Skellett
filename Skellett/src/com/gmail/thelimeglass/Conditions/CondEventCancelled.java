package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondEventCancelled extends Condition {
	
	//[(the|this)] event (1¦is|2¦is(n't| not)) cancelled
	
	private Boolean boo = true;
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (parser.mark == 2) {boo = false;}
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] event (1¦is|2¦is(n't| not)) cancelled";
	}
	public boolean check(Event e) {
		if (!(e instanceof Cancellable)) {
			return false;
		}
		if (((Cancellable) e).isCancelled()) {
			if (boo == true) {
				return true;
			} else {
				return false;
			}
		} else {
			if (boo == false) {
				return true;
			} else {
				return false;
			}
		}
	}
}