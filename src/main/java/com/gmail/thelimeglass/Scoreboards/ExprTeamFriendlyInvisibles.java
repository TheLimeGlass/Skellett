package com.gmail.thelimeglass.Scoreboards;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Team;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprTeamFriendlyInvisibles extends SimpleExpression<Boolean>{
	
	//(score[ ][board]|[skellett[ ]]board) [friendly] invisible[s] [state] [(for|of)] [team] %team%
	
	private Expression<Team> team;
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		team = (Expression<Team>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "(score[ ][board]|[skellett[ ]]board) [friendly] invisible[s] [state] [(for|of)] [team] %team%";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		return new Boolean[]{team.getSingle(e).canSeeFriendlyInvisibles()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			team.getSingle(e).setCanSeeFriendlyInvisibles((Boolean)delta[0]);
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Boolean.class);
		}
		return null;
	}
}