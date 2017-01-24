package com.gmail.thelimeglass.Regenerator;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] regenerator with id %string% (1¦does|2¦does(n't| not)) exist")
@Config("Main.Maps")
@FullConfig
public class CondRegeneratorExists extends Condition {
	
	private Expression<String> ID;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		ID = (Expression<String>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] regenerator with id %string% (1¦does|2¦does(n't| not)) exist";
	}
	public boolean check(Event e) {
		if (RegeneratorManager.contains(ID.getSingle(e))) {
			return isNegated();
		} else {
			return !isNegated();
		}
	}
}