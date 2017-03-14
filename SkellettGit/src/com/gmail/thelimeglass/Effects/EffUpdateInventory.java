package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("update [the] (inventory|menu|gui) %inventory%")
@Config("UpdateInventory")
public class EffUpdateInventory extends Effect {
	
	private Expression<Inventory> inventory;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		inventory = (Expression<Inventory>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "update [the] (inventory|menu|gui) %inventory%";
	}
	@Override
	protected void execute(Event e) {
		if (inventory != null) {
			for (HumanEntity p : inventory.getSingle(e).getViewers()) {
				((Player)p).updateInventory();
			}
		}
	}
}