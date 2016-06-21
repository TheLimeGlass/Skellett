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

public class EffScoreboardSetTitle extends Effect {
	
	//[skellett] set title [(text|string)] [of] [player] [(score|skellett)][ ]board of %player% [(to|with)] %string%
	
	private Expression<Player> player;
	private Expression<String> title;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		title = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] set title [(text|string)] [of] [player] [(score|skellett)][ ]board of %player% [(to|with)] %string%";
	}
	@Override
	protected void execute(Event e) {
		Scoreboard scoreboard = player.getSingle(e).getScoreboard();
		if(scoreboard != null) {
			final SkellettScoreboards board = SkellettScoreboards.of(scoreboard);
			board.setTitle(title.getSingle(e));
		} else {
			return;
		}
	}
}