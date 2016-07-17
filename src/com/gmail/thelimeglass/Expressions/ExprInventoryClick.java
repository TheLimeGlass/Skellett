package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprInventoryClick extends SimpleExpression<ClickType> {
	
	//click[ed] type
	
	public Class<? extends ClickType> getReturnType() {
		return ClickType.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent((Class)InventoryClickEvent.class)) {
			Skript.error((String)"You can not use ClickType expression in any event but inventory click!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Inventory action";
	}
	@Nullable
	protected ClickType[] get(Event e) {
		return new ClickType[]{((InventoryClickEvent)e).getClick()};
	}
}