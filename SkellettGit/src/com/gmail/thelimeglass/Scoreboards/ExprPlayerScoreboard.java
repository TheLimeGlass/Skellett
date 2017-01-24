package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprPlayerScoreboard extends SimpleExpression<Scoreboard>{
	
	//(score[ ][board]|[skellett[ ]]board) of [player] %player%
	//%player%'s (score[ ][board]|[skellett[ ]]board)
	
	private Expression<Player> player;
	@Override
	public Class<? extends Scoreboard> getReturnType() {
		return Scoreboard.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "(score[ ][board]|[skellett[ ]]board) of player %player%";
	}
	@Override
	@Nullable
	protected Scoreboard[] get(Event e) {
		return new Scoreboard[]{player.getSingle(e).getScoreboard()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			player.getSingle(e).setScoreboard((Scoreboard)delta[0]);
		} else if (mode == ChangeMode.REMOVE) {
			player.getSingle(e).setScoreboard(null);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(Scoreboard.class);
		return null;
	}
}