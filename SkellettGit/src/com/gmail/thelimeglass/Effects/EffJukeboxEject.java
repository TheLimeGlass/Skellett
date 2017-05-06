package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Disabled;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[(force|make)] [the] juke[ ]box %block% eject [[it[']s] (record|track|song)]")
@Config("Main.Jukebox")
@FullConfig
@Disabled
public class EffJukeboxEject extends Effect {
	
	private Expression<Block> block;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		block = (Expression<Block>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[(force|make)] [the] juke[ ]box %block% eject [[it[']s] (record|track|song)]";
	}
	@Override
	protected void execute(Event e) {
		if (block != null) {
			((Jukebox)block.getSingle(e)).eject();
		}
	}
}