package com.gmail.thelimeglass.Nametags;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] [(create|set|make)] [a] [name][ ]tag [ID] [(with|named)] [(name|string|text|id)] %string% [(with|from) [player] %-player%]")
@Config("Main.Nametags")
@FullConfig
public class EffCreateNametag extends Effect {
	
	private Expression<String> nametag;
	private Expression<Player> player;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		nametag = (Expression<String>) e[0];
		player = (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] [(create|set|make)] [a] [name][ ]tag [ID] [(with|named)] [(name|string|text|id)] %string% [(with|from) [player] %-player%]";
	}
	@Override
	protected void execute(Event e) {
		Scoreboard board = null;
		if (player != null && player.getSingle(e).getScoreboard() != null) {
			board = player.getSingle(e).getScoreboard();
		}
		NametagManager.createNametag(nametag.getSingle(e), board);
	}
}