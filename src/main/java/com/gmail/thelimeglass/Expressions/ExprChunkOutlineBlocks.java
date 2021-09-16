package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(the|all)] [of] [the] blocks [in [a[n]]] (around|outlin(e|ing)) [of] chunk %chunk% [[at] [y(-| )coordinate] %-number%]")
@Config("ChunkOutlineBlocks")
@PropertyType(ExpressionType.COMBINED)
public class ExprChunkOutlineBlocks extends SimpleExpression<Block> {
	
	private Expression<Chunk> chunk;
	private Expression<Number> y;
	public Class<? extends Block> getReturnType() {
		return Block.class;
	}
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		chunk = (Expression<Chunk>) e[0];
		y = (Expression<Number>) e[1];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[(the|all)] [of] [the] blocks [in [a[n]]] outlin(e|ing) chunk %chunk% [[at] y(-| )coordinate %-number%]";
	}
	@Nullable
	protected Block[] get(Event e) {
		if (chunk != null) {
			ArrayList<Block> blocks = new ArrayList<Block>();
			if (y != null) {
				for (int i = 0; i < 16; i++) {
					blocks.add(chunk.getSingle(e).getBlock(i, y.getSingle(e).intValue(), 0));
					blocks.add(chunk.getSingle(e).getBlock(i, y.getSingle(e).intValue(), 15));
					blocks.add(chunk.getSingle(e).getBlock(0, y.getSingle(e).intValue(), i));
					blocks.add(chunk.getSingle(e).getBlock(15, y.getSingle(e).intValue(), i));
				}
			} else {
				for (int i = 0; i < 16; i++) {
					for (int y = 0; y < 256; y++) {
						blocks.add(chunk.getSingle(e).getBlock(i, y, 0));
						blocks.add(chunk.getSingle(e).getBlock(0, y, i));
						blocks.add(chunk.getSingle(e).getBlock(i, y, 15));
						blocks.add(chunk.getSingle(e).getBlock(15, y, i));
					}
				}
			}
			return blocks.toArray(new Block[blocks.size()]);
		}
		return null;
	}
}