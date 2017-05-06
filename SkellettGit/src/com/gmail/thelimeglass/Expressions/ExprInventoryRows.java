package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] (gui|menu|inventory|chest|window) row[s] (of|from) %inventory%", "%inventory%'s (gui|menu|inventory|chest|window) row[s]"})
@Config("InventoryRows")
@PropertyType(ExpressionType.COMBINED)
public class ExprInventoryRows extends SimpleExpression<Number>{
	
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
		return "[skellett] (gui|menu|inventory|chest|window) row[s] (of|from) %inventory%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{(inventory.getSingle(e)).getSize() / 9};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (inventory != null) {
			Number rows = delta[0] == null ? inventory.getSingle(e).getSize() / 9 : (Number) delta[0];
			if (inventory.getSingle(e).getType() == InventoryType.CHEST || inventory.getSingle(e).getType() == InventoryType.ENDER_CHEST || inventory.getSingle(e).getType() == InventoryType.SHULKER_BOX || inventory.getSingle(e).getType() == InventoryType.PLAYER) {
				if (rows.intValue() >= 1) {
					Inventory copy = Bukkit.createInventory(inventory.getSingle(e).getHolder(), rows.intValue() * 9, inventory.getSingle(e).getName());
					for (HumanEntity human : new ArrayList<>(inventory.getSingle(e).getViewers())) {
						human.openInventory(copy);
					}
					copy.setContents(inventory.getSingle(e).getContents());
				}
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