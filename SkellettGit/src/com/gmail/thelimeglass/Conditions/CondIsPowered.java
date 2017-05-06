package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[block] %block% (1¦(is|has)|2¦(is|has)(n't| not)) [got] [redstone] powered")
@Config("RedstonePower")
public class CondIsPowered extends Condition {
	
	private Expression<Block> block;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		block = (Expression<Block>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[block] %block% (1¦(is|has)|2¦(is|has)(n't| not)) [got] [redstone] powered";
	}
	public boolean check(Event e) {
		if (block.getSingle(e).isBlockPowered()) {
			return isNegated();
		} else {
			return !isNegated();
		}
	}
}