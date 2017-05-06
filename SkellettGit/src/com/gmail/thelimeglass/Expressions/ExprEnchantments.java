package com.gmail.thelimeglass.Expressions;

import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(the|all)] [of] [the] enchant[ment]s (on|of|from) %itemstack%")
@Config("EnchantsOnItem")
@PropertyType(ExpressionType.COMBINED)
public class ExprEnchantments extends SimpleExpression<Enchantment>{
	
	private Expression<ItemStack> item;
	@Override
	public Class<? extends Enchantment> getReturnType() {
		return Enchantment.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		item = (Expression<ItemStack>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(the|all)] [of] [the] enchant[ment]s (on|of|from) %itemstack%";
	}
	@Override
	@Nullable
	protected Enchantment[] get(Event e) {
		Map<Enchantment, Integer> enchantments = item.getSingle(e).getEnchantments();
		return enchantments.keySet().toArray(new Enchantment[enchantments.keySet().size()]);
	}
}