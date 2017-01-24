package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"inventory type of %inventory%", "%inventory%'s [inventory] type"})
@Config("InventoryType")
@PropertyType("COMBINED")
public class ExprInventoryType extends SimpleExpression<InventoryType> {
	
	private Expression<Inventory> inv;
	public Class<? extends InventoryType> getReturnType() {
		return InventoryType.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings({ "unchecked" })
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		inv = (Expression<Inventory>) e[0];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Inventory type";
	}
	@Nullable
	protected InventoryType[] get(Event e) {
		return new InventoryType[]{inv.getSingle(e).getType()};
	}
}