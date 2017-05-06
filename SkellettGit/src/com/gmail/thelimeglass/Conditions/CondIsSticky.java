package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.material.MaterialData;
import org.bukkit.material.PistonBaseMaterial;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[piston] %block% (1¦is|2¦is(n't| not)) [a] sticky [piston]")
@Config("PistonSticky")
public class CondIsSticky extends Condition {
	
	private Expression<Block> block;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		block = (Expression<Block>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[piston] %block% (1¦is|2¦is(n't| not)) [a] sticky [piston]";
	}
	public boolean check(Event e) {
		MaterialData piston = block.getSingle(e).getState().getData();
		if (!(piston instanceof PistonBaseMaterial)) {
			return false;
		}
		if (((PistonBaseMaterial)piston).isSticky()) {
			return isNegated();
		} else {
			return !isNegated();
		}
	}
}