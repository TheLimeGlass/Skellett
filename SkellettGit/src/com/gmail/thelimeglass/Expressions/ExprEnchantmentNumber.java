package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] enchant[ment] level (from|of) %enchantment% (of|in) %itemstack%", "[skellett] %itemstack%'s enchant[ment] level (from|of) %enchantment%"})
@Config("EnchantmentLevel")
@PropertyType("COMBINED")
public class ExprEnchantmentNumber extends SimpleExpression<Number>{
	
	private Expression<Enchantment> enchant;
	private Expression<ItemStack> item;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (matchedPattern == 0) {
			enchant = (Expression<Enchantment>) e[0];
			item = (Expression<ItemStack>) e[1];
		} else {
			item = (Expression<ItemStack>) e[0];
			enchant = (Expression<Enchantment>) e[1];
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] enchant[ment] level (from|of) %enchantment% (of|in) %itemstack%";
	}
	@Override
	protected Number[] get(Event e) {
		 return new Number[]{item.getSingle(e).getEnchantmentLevel(enchant.getSingle(e))};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			Number num = (Number)delta[0];
			if (item.getSingle(e).containsEnchantment(enchant.getSingle(e))) {
				item.getSingle(e).removeEnchantment(enchant.getSingle(e));
			}
			item.getSingle(e).addUnsafeEnchantment(enchant.getSingle(e), num.intValue());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}