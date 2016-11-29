package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprObjectiveDisplaySlot extends SimpleExpression<String>{
	
	//(score[ ][board]|[skellett[ ]]board) objective [display] slot [of] %objective%
	//(score[ ][board]|[skellett[ ]]board) %objective%['s] objective['s] [display] slot
	//(score[ ][board]|[skellett[ ]]board) objective %objective%['s] [display] slot
	
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
		return "[skellett] (score[ ][board]|board) objective [display] slot [of] %objective%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{obj.getSingle(e).getDisplaySlot().toString()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			String string = (String)delta[0];
			if (string.equals("BELOW_NAME")||string.equals("PLAYER_LIST")||string.equals("SIDEBAR")) {
				DisplaySlot slot = DisplaySlot.valueOf(string.replace("\"", "").trim().replace(" ", "_").toUpperCase());
				try {
					slot = DisplaySlot.valueOf(slot.toString().replace("\"", "").trim().replace(" ", "_").toUpperCase());
				} catch (IllegalArgumentException t) {
					return;
				}
				obj.getSingle(e).setDisplaySlot(slot);
			} else {
				return;
			}
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