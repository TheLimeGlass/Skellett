package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("(clear|empty|reset) (inventory|menu|gui) [slot %-integer%] [(of|in)] %inventory%")
@Config("ClearSlot")
public class EffClearSlot extends Effect {
	
	private Expression<Integer> slot;
	private Expression<Inventory> inventory;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		slot = (Expression<Integer>) e[0];
		inventory = (Expression<Inventory>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(clear|empty|reset) (inventory|menu|gui) [slot %-integer%] [(of|in)] %inventory%";
	}
	@Override
	protected void execute(Event e) {
		if (inventory != null) {
			if (slot != null) {
				inventory.getSingle(e).clear(slot.getSingle(e));
			} else {
				inventory.getSingle(e).clear();
			}
		}
	}
}