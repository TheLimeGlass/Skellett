package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Team;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprTeamOptions extends SimpleExpression<Team.OptionStatus>{
	
	//[(score[ ][board]|[skellett[ ]]board)] [team] option[s] [status] %teamoption% [(for|of)] [the] [team] %team%
	
	private Expression<Team.Option> option;
	private Expression<Team> team;
	@Override
	public Class<? extends Team.OptionStatus> getReturnType() {
		return Team.OptionStatus.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		option = (Expression<Team.Option>) e[0];
		team = (Expression<Team>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(score[ ][board]|[skellett[ ]]board)] [team] option[s] [status] %teamoption% [(for|of)] [the] [team] %team%";
	}
	@Override
	@Nullable
	protected Team.OptionStatus[] get(Event e) {
		return new Team.OptionStatus[]{team.getSingle(e).getOption(option.getSingle(e))};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			team.getSingle(e).setOption(option.getSingle(e), (Team.OptionStatus)delta[0]);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Team.OptionStatus.class);
		}
		return null;
	}
}