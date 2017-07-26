package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] (gui|menu|inventory|chest|window) (size|number|slots) (of|from) %inventory%", "%inventory%'s (gui|menu|inventory|chest|window) (size|number|slots)"})
@Config("SizeOfInventory")
@PropertyType(ExpressionType.COMBINED)
public class ExprInventorySize extends SimpleExpression<Number>{
	
	private Expression<Inventory> inventory;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		inventory = (Expression<Inventory>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] (gui|menu|inventory|chest|window) (size|number|slots) (of|from) %inventory%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{(inventory.getSingle(e)).getSize()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (inventory != null) {
			Number rows = delta[0] == null ? inventory.getSingle(e).getSize() : (Number) delta[0];
			if (inventory.getSingle(e).getType() == InventoryType.CHEST) {
				Inventory copy = Bukkit.createInventory(inventory.getSingle(e).getHolder(), rows.intValue(), inventory.getSingle(e).getName());
				for (HumanEntity human : new ArrayList<>(inventory.getSingle(e).getViewers())) {
					human.openInventory(copy);
				}
				copy.setContents(inventory.getSingle(e).getContents());
			} else return;
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}