package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.Utils.Version;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;

@Syntax({"[skellett] [a[n]] %itemstacks% [to be] breakable", "[skellett] [a[n]] break(ing|able) %itemstacks%"})
@Config("Unbreakable")
@Version("1.11.2")
@PropertyType(ExpressionType.PROPERTY)
public class ExprBreakable extends SimplePropertyExpression<ItemStack, ItemStack>{
	
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}
	@Override
	protected String getPropertyName() {
		return "[skellett] [a[n]] break(ing|able) %itemstacks%";
	}
	@Override
	@Nullable
	public ItemStack convert(ItemStack item) {
		ItemMeta metadata = item.getItemMeta();
		metadata.setUnbreakable(false);
	    item.setItemMeta(metadata);
	    return item;
	}
}