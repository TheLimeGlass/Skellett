package com.gmail.thelimeglass.Effects;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Jukebox;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Disabled;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

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
			BlockState state = block.getSingle(e).getState();
			if (state instanceof Jukebox) {
				Jukebox jukebox = (Jukebox) state;
				jukebox.eject();
				jukebox.update();
			}
		}
	}
}