package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("[skellett] hide [player] %player% from %player%")
@Config("HideShowPlayers")
public class EffHidePlayer extends Effect {
	
	private Expression<Player> players, hider;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parse) {
		hider = (Expression<Player>) e[0];
		players = (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] hide [player] %player% from %players%";
	}
	@Override
	protected void execute(Event e) {
		if (players != null || hider != null) {
			for (Player p : players.getAll(e)) {
				p.showPlayer(hider.getSingle(e));
			}
		}
	}
}
