package me.limeglass.skellett.elements.expressions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprChunkOutlineBlocks extends SimpleExpression<Block> {

	static {
		Skript.registerExpression(ExprChunkOutlineBlocks.class, Block.class, ExpressionType.SIMPLE, "[all [of]] [the] blocks [in [a[n]]] (around|outlin(e|ing)) [of] chunk %chunks% [[at] [y(-| )coordinate] %-number%]");
	}

	private Expression<Chunk> chunks;
	private Expression<Number> y;

	public Class<? extends Block> getReturnType() {
		return Block.class;
	}

	public boolean isSingle() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		chunks = (Expression<Chunk>) expressions[0];
		y = (Expression<Number>) expressions[1];
		return true;
	}

	public String toString(@Nullable Event event, boolean debug) {
		if (debug || chunks == null)
			return "blocks around chunk";
		return "blocks around chunks: " + chunks.toString(event, debug);
	}

	@Nullable
	protected Block[] get(Event event) {
		if (chunks == null)
			return null;
		List<Block> blocks = new ArrayList<>();
		Chunk[] chunks = this.chunks.getArray(event);
		if (y != null) {
			int y = this.y.getSingle(event).intValue();
			for (Chunk chunk : chunks) {
				for (int i = 0; i < 16; i++) {
					blocks.add(chunk.getBlock(i, y, 0));
					blocks.add(chunk.getBlock(i, y, 15));
					blocks.add(chunk.getBlock(0, y, i));
					blocks.add(chunk.getBlock(15, y, i));
				}
			}
		} else {
			for (Chunk chunk : chunks) {
				for (int i = 0; i < 16; i++) {
					for (int y = 0; y < chunk.getWorld().getMaxHeight(); y++) {
						blocks.add(chunk.getBlock(i, y, 0));
						blocks.add(chunk.getBlock(0, y, i));
						blocks.add(chunk.getBlock(i, y, 15));
						blocks.add(chunk.getBlock(15, y, i));
					}
				}
			}
		}
		return blocks.toArray(new Block[0]);
	}

}
