package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("(make|force) spawner [at] %block% to spawn [[a[n]] entit(y|ies)]")
@Config("Main.Spawners")
@FullConfig
public class EffSpawnerForce extends Effect {
	
	private Expression<Block> spawner;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		spawner = (Expression<Block>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(make|force) spawner [at] %block% to spawn [[a[n]] entity]";
	}
	@Override
	protected void execute(Event e) {
		if (spawner != null) {
			int delay = ((CreatureSpawner)spawner.getSingle(e)).getDelay();
			CreatureSpawner state = (CreatureSpawner)spawner.getSingle(e).getState();
			state.setDelay(0);
			state.update();
			state.setDelay(delay);
			state.update();
		}
	}
}
