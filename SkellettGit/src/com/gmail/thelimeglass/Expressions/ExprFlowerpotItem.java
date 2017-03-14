package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.block.FlowerPot;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Disabled;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] item (in|inside|within|of|from) [flower[ ]]pot %block%", "[skellett] %block%'s [flower[ ]]pot item", "[skellett] [flower[ ]]pot %block%'s item"})
@Config("FlowerpotItem") //re-add this in the config
@Disabled
@PropertyType(ExpressionType.COMBINED)
public class ExprFlowerpotItem extends SimpleExpression<ItemStack>{
	
	private Expression<Block> block;
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		block = (Expression<Block>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "item (in|inside|within|of|from) [flower[ ]]pot %block%";
	}
	@Override
	@Nullable
	protected ItemStack[] get(Event e) {
		if (block != null) {
			if (block.getSingle(e) instanceof FlowerPot) {
				return new ItemStack[]{((FlowerPot)block.getSingle(e)).getContents().toItemStack()};
			}
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (block != null) {
				if (block.getSingle(e) instanceof FlowerPot) {
					MaterialData mad = ((ItemStack)delta[0]).getData();
					((FlowerPot)block.getSingle(e)).setContents(mad);
				}
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(ItemStack.class);
		}
		return null;
	}
}