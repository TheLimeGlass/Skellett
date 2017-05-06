package com.gmail.thelimeglass.Nametags;

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

@Syntax("[skellett] [get] prefix [of] [the] [name][ ]tag [with] [id] %string%")
@PropertyType(ExpressionType.COMBINED)
@Config("Main.Nametags")
@FullConfig
public class ExprGetNametagPrefix extends SimpleExpression<String>{
	
	private Expression<String> nametag;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		nametag = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [get] prefix [of] [the] [name][ ]tag [with] [id] %string%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{NametagManager.getNametagPrefix(nametag.getSingle(e))};
	}
}