package com.gmail.thelimeglass.Nametags;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] add %player% to [the] [name][ ]tag [(with|of)] [id] %string% [(with|from) [[score][ ]board] %-scoreboard%]")
@Config("Main.Nametags")
@FullConfig
public class EffAddPlayerNametag extends Effect {
	
	private Expression<Player> player;
	private Expression<String> nametag;
	private Expression<Scoreboard> scoreboard;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		nametag = (Expression<String>) e[1];
		scoreboard = (Expression<Scoreboard>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] add %player% to [the] [name][ ]tag [(with|of)] [id] %string% [[score][ ]board] %-scoreboard%]";
	}
	@Override
	protected void execute(Event e) {
		Scoreboard board = null;
		if (scoreboard != null && scoreboard.getSingle(e) != null) {
			board = scoreboard.getSingle(e);
		}
		NametagManager.addPlayer(player.getSingle(e), nametag.getSingle(e), board);
	}
}
