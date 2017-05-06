package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;
import org.bukkit.Location;
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

@Syntax("[(the|all)] [of] [the] [possible] drop[(ped|s)] [items] (from|of) [block [at]] %location% [(with|using) %-itemstack%]")
@Config("BlockStates")
@PropertyType(ExpressionType.COMBINED)
public class ExprBlockGetDrops extends SimpleExpression<ItemStack> {
	
	private Expression<Location> loc;
	private Expression<ItemStack> item;
	@Override
	public boolean isSingle() {
		return false;
	}
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}
	@Override
	public String toString(Event event, boolean b) {
		return "[(the|all)] [of] [the] [possible] drop[(ped|s)] [items] (from|of) [block [at]] %location% [(with|using) %-itemstack%]";
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		loc = (Expression<Location>) e[0];
		item = (Expression<ItemStack>) e[1];
		return true;
	}
	@Override
	protected ItemStack[] get(final Event e) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		if (item != null) {
			for (final ItemStack item : loc.getSingle(e).getBlock().getDrops(item.getSingle(e))) {
				if (item != null) {
					drops.add((ItemStack)item);
				}
			}
		} else {
			for (final ItemStack item : loc.getSingle(e).getBlock().getDrops()) {
				if (item != null) {
					drops.add((ItemStack)item);
				}
			}
		}
		return drops.toArray(new ItemStack[drops.size()]);
	}
}