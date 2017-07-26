package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import com.gmail.thelimeglass.Utils.Utils;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[the] click[ed] row")
@Config("ClickedRow")
@PropertyType(ExpressionType.SIMPLE)
public class ExprClickedRow extends SimpleExpression<Number> {
	
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(InventoryClickEvent.class)) {
			Skript.error("You can not use clicked row expression in any event but inventory click!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "clicked row";
	}
	@Nullable
	protected Number[] get(Event e) {
		InventoryType type = ((InventoryClickEvent)e).getClickedInventory().getType();
		if (type != InventoryType.CHEST || type != InventoryType.DISPENSER || type != InventoryType.DROPPER || type != InventoryType.WORKBENCH || type != InventoryType.ENDER_CHEST || type != InventoryType.SHULKER_BOX || type != InventoryType.PLAYER) {
			for (int i = 1; i < ((InventoryClickEvent)e).getClickedInventory().getSize() + 1; i++) {
				if (Utils.ofRow(i, ((InventoryClickEvent)e).getSlot(), ((InventoryClickEvent)e).getInventory())) {
					return new Number[]{i};
				}
			}
		}
		return null;
	}
}