package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprObjectiveDisplayName extends SimpleExpression<String>{
	
	//(score[ ][board]|[skellett[ ]]board) objective display name [of] %objective%
	//(score[ ][board]|[skellett[ ]]board) %objective%['s] objective['s] display name
	//(score[ ][board]|[skellett[ ]]board) objective %objective%['s] display name
	
	private Expression<Objective> obj;
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
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		obj = (Expression<Objective>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "(score[ ][board]|[skellett[ ]]board) objective display name [of] %objective%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{obj.getSingle(e).getDisplayName()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			obj.getSingle(e).setDisplayName((String)delta[0]);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(String.class);
		return null;
	}
}