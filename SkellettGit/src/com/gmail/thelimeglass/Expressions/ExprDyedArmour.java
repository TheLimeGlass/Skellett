package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Color;
import ch.njol.util.Kleenean;

@Syntax("[skellett] [dye[d]] [colo[u]r] [of] %itemstack% [to] (colo[u]r[ed]|dyed) %color%")
@Config("Dyed")
@PropertyType(ExpressionType.COMBINED)
public class ExprDyedArmour extends SimpleExpression<ItemStack>{
	
	private Expression<ItemStack> item;
	private Expression<Color> colour;
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
		item = (Expression<ItemStack>) e[0];
		colour = (Expression<Color>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [dye[d]] [colo[u]r] [of] %itemstack% [to] (colo[u]r[ed]|dyed) %color%";
	}
	@Override
	@Nullable
	protected ItemStack[] get(Event e) {
		ItemStack itemstack = item.getSingle(e);
		if (itemstack.getType() == Material.LEATHER_HELMET || itemstack.getType() == Material.LEATHER_CHESTPLATE || itemstack.getType() == Material.LEATHER_LEGGINGS || itemstack.getType() == Material.LEATHER_BOOTS) {
			LeatherArmorMeta armour = (LeatherArmorMeta) itemstack.getItemMeta();
			armour.setColor(colour.getSingle(e).getBukkitColor());
			itemstack.setItemMeta((ItemMeta)armour);
		}
		return new ItemStack[]{itemstack};
	}
}