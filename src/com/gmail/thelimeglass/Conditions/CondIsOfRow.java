package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[slot] %number% (1¦is|2¦is(n't| not)) (within|of|in) row %number% [(of|in|from) [inventory] %-inventory%]")
@Config("OfRow")
public class CondIsOfRow extends Condition {
	
	private Expression<Number> slot, row;
	private Expression<Inventory> inventory;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		slot = (Expression<Number>) e[0];
		row = (Expression<Number>) e[1];
		inventory = (Expression<Inventory>) e[2];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[slot] %number% (1¦is|2¦is(n't| not)) (within|of|in) row %number% [(of|in|from) [inventory] %-inventory%]";
	}
	public boolean check(Event e) {
		if (slot != null && row != null) {
			Integer mod = 9;
			if (inventory != null) {
				if (inventory.getSingle(e).getType() == InventoryType.DISPENSER ||inventory.getSingle(e).getType() == InventoryType.WORKBENCH || inventory.getSingle(e).getType() == InventoryType.DROPPER) {
					mod = 3;
				} else if (inventory.getSingle(e).getType() == InventoryType.CHEST || inventory.getSingle(e).getType() == InventoryType.SHULKER_BOX || inventory.getSingle(e).getType() == InventoryType.ENDER_CHEST || inventory.getSingle(e).getType() == InventoryType.PLAYER){
					mod = 9;
				} else {
					mod = inventory.getSingle(e).getSize();
				}
			}
			Integer calculate = row.getSingle(e).intValue() * mod;
			return slot.getSingle(e).intValue() >= calculate - mod && slot.getSingle(e).intValue() < calculate ? isNegated() : !isNegated();
		}
		return false;
	}
}