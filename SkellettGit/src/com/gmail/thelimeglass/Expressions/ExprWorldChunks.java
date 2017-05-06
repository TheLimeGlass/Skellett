package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(the|all)] [of] [the] loaded chunks (in|of|from) [world] %world%")
@Config("Chunks")
@PropertyType(ExpressionType.COMBINED)
public class ExprWorldChunks extends SimpleExpression<Chunk>{
	
	private Expression<World> world;
	@Override
	public Class<? extends Chunk> getReturnType() {
		return Chunk.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		world = (Expression<World>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(the|all)] [of] [the] loaded chunks (in|of|from) [world] %world%";
	}
	@Override
	@Nullable
	protected Chunk[] get(Event e) {
		return world.getSingle(e).getLoadedChunks();
	}
}