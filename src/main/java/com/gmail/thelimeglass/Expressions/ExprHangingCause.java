package com.gmail.thelimeglass.Expressions;

import org.bukkit.event.Event;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[the] [un](hung|hang)[(ed|ing)] cause")
@Config("Syntax.Events.Hanging")
@FullConfig
@PropertyType(ExpressionType.SIMPLE)
public class ExprHangingCause extends SimpleExpression<String> {
	
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(HangingBreakEvent.class) && ScriptLoader.isCurrentEvent(HangingBreakByEntityEvent.class)) {
			Skript.error("You can not use HangingCause expression in any event but on unhang!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Unhang cause";
	}
	@Nullable
	protected String[] get(Event e) {
		return new String[]{((HangingBreakEvent)e).getCause().toString()};
	}
}