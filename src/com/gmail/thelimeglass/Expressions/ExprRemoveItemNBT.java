package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

public class ExprRemoveItemNBT extends SimplePropertyExpression<ItemStack, ItemStack>{
	
	//%itemstacks% with [all] removed NBT
	//remove[ed] [all] NBT [from] %itemstacks%
	
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}
	@Override
	protected String getPropertyName() {
		return "%itemstacks% with [all] removed NBT";
	}
	@Override
	@Nullable
	public ItemStack convert(ItemStack item) {
		ItemMeta metadata = item.getItemMeta();
		metadata.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    metadata.removeItemFlags(ItemFlag.HIDE_DESTROYS);
	    metadata.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
	    metadata.removeItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	    item.setItemMeta(metadata);
	    return item;
	}
}