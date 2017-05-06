package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;

@Syntax("[skellett] serialize [variable] %object%")
@Config("Serialize")
public class EffSerialze extends Effect {
	
	private Expression<Object> object;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		object = (Expression<Object>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] serialize [variable] %object%";
	}
	@Override
	protected void execute(Event e) {
		Variables.serialize(object.getSingle(e));
	}
}