package com.gmail.thelimeglass.Effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class EffOpenInventory extends Effect {

	//[skellett] open [[better] inventory [type]] %string% [with %-number% row[s]] [named %-string%] to %players%
	
	private Expression<String> invType;
	private Expression<Number> rows;
	private Expression<String> header;
	private Expression<Player> players;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		invType = (Expression<String>) e[0];
		rows = (Expression<Number>) e[1];
		header = (Expression<String>) e[2];
		players = (Expression<Player>) e[3];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] open [[better] inventory [type]] %string% [with %-number% row[s]] [named %-string%] to %players%";
	}
	@Override
	protected void execute(Event e) {
		InventoryType type = InventoryType.valueOf(invType.getSingle(e).replace("\"", "").trim().replace(" ", "_").toUpperCase());
		try {
			type = InventoryType.valueOf(type.toString().replace("\"", "").trim().replace(" ", "_").toUpperCase());
		} catch (IllegalArgumentException t) {
			Skript.error(type.toString() + " Unknown inventory type");
			return;
		}
		Inventory inv = Bukkit.createInventory(null, type);
		if (rows != null && type == InventoryType.CHEST) {
			Float num = (rows.getSingle(e).floatValue()) * 9;
			if (header != null) {
				inv = Bukkit.createInventory(null, num.intValue(), header.getSingle(e));
			} else {
				inv = Bukkit.createInventory(null, num.intValue());
			}
		} else if (rows == null && type != InventoryType.CHEST) {
			if (header != null) {
				inv = Bukkit.createInventory(null, type, header.getSingle(e));
			}
		} else if (rows != null && type != InventoryType.CHEST) {
			if (header != null) {
				inv = Bukkit.createInventory(null, type, header.getSingle(e));
			}
		}
		for (Player p : players.getAll(e)) {
			if (p.getOpenInventory() != null) {
				p.closeInventory();
			}
			p.openInventory(inv);
		}
	}
}
