package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.Chunk;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] load chunk %chunk% [[with] generat(e|ing) %-boolean%]")
@Config("Chunks")
public class EffChunkLoad extends Effect {
	
	private Expression<Chunk> chunk;
	private Expression<Boolean> generate;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		chunk = (Expression<Chunk>) e[0];
		generate = (Expression<Boolean>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] load chunk %chunk% [[with] generat(e|ing) %-boolean%]";
	}
	@Override
	protected void execute(Event e) {
		Boolean generating = true;
		if (chunk != null) {
			if (generate != null) {
				generating = generate.getSingle(e);
			}
			chunk.getSingle(e).load(generating);
		}
	}
}