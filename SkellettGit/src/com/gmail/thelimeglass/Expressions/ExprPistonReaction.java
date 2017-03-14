package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterEnum;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[the] piston [move] reaction (of|from) %block%", "%block%'s piston [move] reaction"})
@Config("PistonReaction")
@PropertyType(ExpressionType.COMBINED)
@RegisterEnum("pistonreaction")
public class ExprPistonReaction extends SimpleExpression<PistonMoveReaction> {
	
	private Expression<Block> block;
	public Class<? extends PistonMoveReaction> getReturnType() {
		return PistonMoveReaction.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parse) {
		block = (Expression<Block>) e[0];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Teleport cause";
	}
	@Nullable
	protected PistonMoveReaction[] get(Event e) {
		return new PistonMoveReaction[]{block.getSingle(e).getPistonMoveReaction()};
	}
}