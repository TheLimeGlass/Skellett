package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[the] [skellett] [click[ed]] cursor")
@Config("ClickedCursor")
@PropertyType(ExpressionType.SIMPLE)
public class ExprClickedCursor extends SimpleExpression<ItemType> {
	
	public Class<? extends ItemType> getReturnType() {
		return ItemType.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(InventoryClickEvent.class)) {
			Skript.error("You can not use clicked cursor expression in any event but inventory click!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "clicked cursor";
	}
	@Nullable
	protected ItemType[] get(Event e) {
		ItemType item = new ItemType(((InventoryClickEvent)e).getCursor());
		return new ItemType[]{item};
	}
}