package me.limeglass.skellett.elements.expressions;

import org.bukkit.block.Block;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.limeglass.skellett.objects.BlockSave;

public class ExprBlockSave extends SimplePropertyExpression<Block, BlockSave>{

	static {
		register(ExprBlockSave.class, BlockSave.class, "block save[s]", "blocks");
	}

	@Override
	public Class<? extends BlockSave> getReturnType() {
		return BlockSave.class;
	}

	@Override
	@Nullable
	public BlockSave convert(Block block) {
		return new BlockSave(block);
	}

	@Override
	protected String getPropertyName() {
		return "block save";
	}

}
