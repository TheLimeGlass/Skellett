package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;

@Syntax({"%itemstacks% with hid(den|ing) enchant[ment][s]", "[skellett] (shiny|hidden enchant[ment][s]|glow|glowing) [item] %itemstack%"})
@Config("NoNBT")
@PropertyType(ExpressionType.PROPERTY)
public class ExprHideEnchants extends SimplePropertyExpression<ItemStack, ItemStack>{
	
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}
	@Override
	protected String getPropertyName() {
		return "%itemstacks% with hid(den|ing) enchant[ment][s]";
	}
	@Override
	@Nullable
	public ItemStack convert(ItemStack item) {
		if (item.getType() == Material.FISHING_ROD) {
			item.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
		} else {
			item.addUnsafeEnchantment(Enchantment.LURE, 1);
		}
		ItemMeta metadata = item.getItemMeta();
	    metadata.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    item.setItemMeta(metadata);
	    return item;
	}
}