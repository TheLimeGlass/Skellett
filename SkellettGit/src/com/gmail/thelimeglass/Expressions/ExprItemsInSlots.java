package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;
import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax("[items (from|of|in)] slots %numbers% (from|of|in) %inventory%")
@Config("ItemInSlots")
@PropertyType(ExpressionType.COMBINED)
public class ExprItemsInSlots extends SimpleExpression<ItemStack>{
	
	private Expression<Number> slots;
	private Expression<Inventory> inventory;
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		slots = (Expression<Number>) e[0];
		inventory = (Expression<Inventory>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[items (from|of|in)] slots %numbers% (from|of|in) %inventory%";
	}
	@Override
	@Nullable
	protected ItemStack[] get(Event e) {
		if (inventory != null) {
			ArrayList<ItemStack> inventorySlots = new ArrayList<ItemStack>();
			for (Number s : slots.getAll(e)) {
				inventorySlots.add(inventory.getSingle(e).getItem(s.intValue()));
			}
			return inventorySlots.toArray(new ItemStack[inventorySlots.size()]);
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (inventory != null && delta[0] != null) {
				for (Number s : slots.getAll(e)) {
					inventory.getSingle(e).setItem(s.intValue(), ((ItemType)delta[0]).getRandom());
				}
			}
		} else if (mode == ChangeMode.REMOVE || mode == ChangeMode.REMOVE_ALL || mode == ChangeMode.RESET || mode == ChangeMode.DELETE) {
			if (inventory != null && delta[0] != null) {
				for (Number s : slots.getAll(e)) {
					inventory.getSingle(e).clear(s.intValue());
				}
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.REMOVE || mode == ChangeMode.REMOVE_ALL || mode == ChangeMode.RESET || mode == ChangeMode.DELETE) {
			return CollectionUtils.array(ItemType.class);
		}
		return null;
	}
}