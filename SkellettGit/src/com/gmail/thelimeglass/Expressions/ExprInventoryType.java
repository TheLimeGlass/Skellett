package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.gmail.thelimeglass.Utils.Annotations.AntiDependencyEnum;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.RegisterEnum;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"inventory type of %inventory%", "%inventory%'s inventory type"})
@Config("InventoryType")
@PropertyType(ExpressionType.COMBINED)
@RegisterEnum(ExprClass=InventoryType.class, value="inventorytype")
@AntiDependencyEnum({"SkQuery", "SkStuff"})
public class ExprInventoryType extends SimpleExpression<String> {
	
	private Expression<Inventory> inv;
	public Class<? extends String> getReturnType() {
		return String.class;
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
	protected String[] get(Event e) {
		if (inv != null && inv.getSingle(e).getType() != null) {
			return new String[]{inv.getSingle(e).getType().toString()};
		}
		return null;
	}
}