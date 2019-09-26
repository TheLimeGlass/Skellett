package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

@Syntax({"teleport %players% to [world] spawn (of|in) [world] %string%", "[skellett] teleport %players% to world %string% [spawn]"})
@Config("TeleportPlayerWorldSpawn")
public class EffPlayerTeleportWorldSpawn extends Effect {

	private Expression<Player> players;
	private Expression<String> world;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		players = (Expression<Player>) e[0];
		world = (Expression<String>) e[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "teleport %players% to [world] spawn of [world] %string%";
	}

	@Override
	protected void execute(Event e) {
		World w = Bukkit.getWorld(world.getSingle(e));
		if (w == null)
			return;
		for (Player player : players.getArray(e)) {
			player.teleport(w.getSpawnLocation());
		}
	}

}
