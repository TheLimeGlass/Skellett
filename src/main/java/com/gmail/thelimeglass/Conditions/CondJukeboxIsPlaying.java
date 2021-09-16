package com.gmail.thelimeglass.Conditions;

import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("juke[ ]box %block% (1¦is|2¦is(n't| not)) playing [a] (record|track|song)")
@Config("Main.Jukebox")
@FullConfig
public class CondJukeboxIsPlaying extends Condition {
	
	private Expression<Block> block;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		block = (Expression<Block>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "juke[ ]box %block% (1¦is|2¦is(n't| not)) playing [a] (record|track|song)";
	}
	public boolean check(Event e) {
		if (block.getSingle(e) instanceof Jukebox) {
			if (((Jukebox)block.getSingle(e)).isPlaying()) {
				return isNegated();
			} else {
				return !isNegated();
			}
		}
		return false;
	}
}