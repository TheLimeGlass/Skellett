package com.gmail.thelimeglass.Expressions;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(the|all)] [of] [the] [player[']s] view(er[s]|ing) [of] %inventory%")
@Config("InventoryViewers")
@PropertyType(ExpressionType.SIMPLE)
public class ExprInventoryViewers extends SimpleExpression<HumanEntity> {
	
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
		return inventory.getSingle(e).getViewers().toArray(new HumanEntity[inventory.getSingle(e).getViewers().size()]);
	}
}