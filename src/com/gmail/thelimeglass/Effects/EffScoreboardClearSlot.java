package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;
import com.gmail.thelimeglass.SkellettAPI.SkellettScoreboards;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffScoreboardClearSlot extends Effect {
	
	//[skellett] clear [(line|value|slot)] [(text|string)] [of] [(score|skellett)][ ]board [in] [(line|value|slot)] %integer% of %player%
	
	private Expression<Integer> slot;
	private Expression<Player> player;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		slot = (Expression<Integer>) e[0];
		player = (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] clear [(line|value|slot)] [(text|string)] [of] [(score|skellett)][ ]board [in] [(line|value|slot)] %integer% of %player%";
	}
	@Override
	protected void execute(Event e) {
		Scoreboard scoreboard = player.getSingle(e).getScoreboard();
		if(scoreboard != null) {
			final SkellettScoreboards board = SkellettScoreboards.of(scoreboard);
			board.clearText(slot.getSingle(e));
		} else {
			return;
		}
	}
}