package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprInventoryViewers extends SimpleExpression<HumanEntity> {
	
	//[(the|all)] [of] [the] [player[']s] view(er[s]|ing) [of] %inventory%
	
	private Expression<Inventory> inventory;
	@Override
	public boolean isSingle() {
		return false;
	}
	@Override
	public Class<? extends HumanEntity> getReturnType() {
		return HumanEntity.class;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		inventory = (Expression<Inventory>) e[0];
		return true;
	}
	@Override
	public String toString(Event event, boolean b) {
		return "[(the|all)] [of] [the] [player[']s] view(er[s]|ing) [of] %inventory%";
	}
	@Override
	protected HumanEntity[] get(final Event e) {
		ArrayList<HumanEntity> players = new ArrayList<>();
		players.addAll(inventory.getSingle(e).getViewers());
		return players.toArray(new HumanEntity[players.size()]);
	}
}