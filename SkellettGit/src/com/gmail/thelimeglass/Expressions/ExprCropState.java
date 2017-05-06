package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

@Syntax({"crop state of %block%", "%block%'s crop state"})
@Config("CropState")
@PropertyType(ExpressionType.COMBINED)
@RegisterEnum("cropstate")
public class ExprCropState extends SimpleExpression<CropState> {
	
	private Expression<Block> block;
	public Class<? extends CropState> getReturnType() {
		return CropState.class;
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
	protected CropState[] get(Event e) {
		if (block.getSingle(e).getType().equals(Material.CROPS)) {
			if (block.getSingle(e).getData() == CropState.RIPE.getData()){
				return new CropState[]{CropState.RIPE};
			} else if (block.getSingle(e).getData() == CropState.GERMINATED.getData()){
				return new CropState[]{CropState.GERMINATED};
			} else if (block.getSingle(e).getData() == CropState.MEDIUM.getData()){
				return new CropState[]{CropState.MEDIUM};
			} else if (block.getSingle(e).getData() == CropState.SEEDED.getData()){
				return new CropState[]{CropState.SEEDED};
			} else if (block.getSingle(e).getData() == CropState.TALL.getData()){
				return new CropState[]{CropState.TALL};
			} else if (block.getSingle(e).getData() == CropState.VERY_SMALL.getData()){
				return new CropState[]{CropState.VERY_SMALL};
			} else if (block.getSingle(e).getData() == CropState.VERY_TALL.getData()){
				return new CropState[]{CropState.VERY_TALL};
			} else {
				return null;
			}
		}
		return null;
	}
}