package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondObjectiveIsModifiable extends Condition {

	//(score[ ][board]|[skellett[ ]]board) objective %objective% (1¦is modifiable|2¦is(n't| not) modifiable)
	
	private Expression<Objective> obj;
	Boolean boo = true;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		obj = (Expression<Objective>) e[0];
		if (parser.mark == 2) {boo = false;}
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "(score[ ][board]|[skellett[ ]]board) objective %objective% (1¦is modifiable|2¦is(n't| not) modifiable)";
	}
	public boolean check(Event e) {
		if (obj.getSingle(e).isModifiable()) {
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