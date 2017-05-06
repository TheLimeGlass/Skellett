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

@Syntax("[skellett] show [player] %player% to %players%")
@Config("HideShowPlayers")
public class EffShowPlayer extends Effect {
	
	private Expression<Player> players, shower;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parse) {
		shower = (Expression<Player>) e[0];
		players= (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] show [player] %player% to %players%";
	}
	@Override
	protected void execute(Event e) {
		if (players != null || shower != null) {
			for (Player p : players.getAll(e)) {
				p.showPlayer(shower.getSingle(e));
			}
		}
	}
}
