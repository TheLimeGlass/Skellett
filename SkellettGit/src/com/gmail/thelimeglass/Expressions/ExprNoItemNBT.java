package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;

@Syntax("%itemstacks% with(out [any]| no) NBT")
@Config("NoNBT")
@PropertyType(ExpressionType.PROPERTY)
public class ExprNoItemNBT extends SimplePropertyExpression<ItemStack, ItemStack>{
	
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}
	@Override
	protected String getPropertyName() {
		return "%itemstack% with([out] any| no) NBT";
	}
	@Override
	@Nullable
	public ItemStack convert(ItemStack item) {
		ItemMeta metadata = item.getItemMeta();
		metadata.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    metadata.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    metadata.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    metadata.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	    item.setItemMeta(metadata);
	    return item;
	}
}