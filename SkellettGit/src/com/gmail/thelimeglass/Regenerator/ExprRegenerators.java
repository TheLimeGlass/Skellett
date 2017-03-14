package com.gmail.thelimeglass.Regenerator;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(the|all)] [of] [the] [skellett] regenerator[s] [ids]")
@Config("Main.Regenerator")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprRegenerators extends SimpleExpression<String> {
	
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	public boolean isSingle() {
		return false;
	}
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[(the|all)] [of] [the] [skellett] regenerator [ids]";
	}
	@Nullable
	protected String[] get(Event e) {
		return RegeneratorManager.getAll();
	}
}