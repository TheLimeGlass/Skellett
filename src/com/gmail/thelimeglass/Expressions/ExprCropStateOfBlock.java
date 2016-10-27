package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprCropStateOfBlock extends SimpleExpression<String> {
	
	//crop state of %block%
	
	private Expression<Block> block;
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		block = (Expression<Block>) e[0];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Crop State";
	}
	@SuppressWarnings("deprecation")
	@Nullable
	protected String[] get(Event e) {
		if (block.getSingle(e).getType().equals(Material.CROPS)) {
			if (block.getSingle(e).getData() == CropState.RIPE.getData()){
				return new String[]{"RIPE"};
			} else if (block.getSingle(e).getData() == CropState.GERMINATED.getData()){
				return new String[]{"GERMINATED"};
			} else if (block.getSingle(e).getData() == CropState.MEDIUM.getData()){
				return new String[]{"MEDIUM"};
			} else if (block.getSingle(e).getData() == CropState.SEEDED.getData()){
				return new String[]{"SEEDED"};
			} else if (block.getSingle(e).getData() == CropState.TALL.getData()){
				return new String[]{"TALL"};
			} else if (block.getSingle(e).getData() == CropState.VERY_SMALL.getData()){
				return new String[]{"VERY_SMALL"};
			} else if (block.getSingle(e).getData() == CropState.VERY_TALL.getData()){
				return new String[]{"VERY_TALL"};
			} else {
				return null;
			}
		}
		return null;
	}
}