package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprClickedSlotType extends SimpleExpression<InventoryType.SlotType> {
	
	//click[ed] slot type
	
	public Class<? extends InventoryType.SlotType> getReturnType() {
		return InventoryType.SlotType.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(InventoryClickEvent.class)) {
			Skript.error("You can not use clicked slot type expression in any event but inventory click!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Inventory clicked slot type";
	}
	@Nullable
	protected InventoryType.SlotType[] get(Event e) {
		return new InventoryType.SlotType[]{((InventoryClickEvent)e).getSlotType()};
	}
}