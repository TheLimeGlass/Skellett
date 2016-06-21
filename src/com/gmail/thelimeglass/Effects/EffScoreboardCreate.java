package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.gmail.thelimeglass.Main;
import com.gmail.thelimeglass.SkellettAPI.SkellettScoreboards;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffScoreboardCreate extends Effect {
	
	//[skellett] (create|make|register) [new] [(score|skellett)][ ]board [with] [title] %string% (for|to) %player%
	
	private Expression<String> title;
	private Expression<Player> player;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		title = (Expression<String>) e[0];
		player = (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (create|make|register) [new] [(score|skellett)][ ]board [with] [title] %string% for %player%";
	}
	@Override
	protected void execute(Event e) {
		final ScoreboardManager manager = Bukkit.getScoreboardManager();
		final SkellettScoreboards skellettboard = SkellettScoreboards.of(manager.getNewScoreboard());
		boolean update = false;
		Scoreboard scoreboard = player.getSingle(e).getScoreboard();
		if(scoreboard == null) {
			scoreboard = skellettboard.getNewScoreboard();
			update = true;
		}
		final SkellettScoreboards board = SkellettScoreboards.of(scoreboard);
		board.setTitle(Main.cc(title.getSingle(e)));
		if (update) {
			player.getSingle(e).setScoreboard(scoreboard);
		}
	}
}