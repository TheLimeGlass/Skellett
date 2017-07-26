package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;

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

@Syntax("[the] (hung|hang)[(ed|ing)] remover [entity]")
@Config("Syntax.Events.Hanging")
@FullConfig
@PropertyType(ExpressionType.SIMPLE)
public class ExprHangingRemover extends SimpleExpression<Entity> {
	
	public Class<? extends Entity> getReturnType() {
		return Entity.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(HangingBreakByEntityEvent.class) && !ScriptLoader.isCurrentEvent(HangingBreakEvent.class)) {
			Skript.error("You can not use HangingCause expression in any event but on unhang remove!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Unhang cause";
	}
	@Nullable
	protected Entity[] get(Event e) {
		return new Entity[]{((HangingBreakByEntityEvent)e).getRemover()};
	}
}