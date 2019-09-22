package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.AnvilInventory;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.gmail.thelimeglass.Utils.Annotations.Version;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[the] [skellett] [anvil[s]] repair cost (of|from|in) [anvil] [inventory] %anvilinventory%", "[skellett] [anvil] [inventory] %anvilinventory%'s [anvil] repair cost"})
@Config("Syntax.Events.AnvilPrepare")
@FullConfig
@Version("1.11.2")
@PropertyType(ExpressionType.COMBINED)
public class ExprAnvilPrepareInventoryCost extends SimpleExpression<Number>{
	
	private Expression<AnvilInventory> inventory;
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
		inventory = (Expression<AnvilInventory>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] [skellett] [anvil[s]] repair cost (of|from|in) [anvil] [inventory] %anvilinventory%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{inventory.getSingle(e).getRepairCost()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number num = (Number)delta[0];
		Number numNow = inventory.getSingle(e).getRepairCost();
		if (mode == ChangeMode.SET) {
			inventory.getSingle(e).setRepairCost(num.intValue());
		} else if (mode == ChangeMode.RESET) {
			inventory.getSingle(e).setRepairCost(0);
		} else if (mode == ChangeMode.ADD) {
			inventory.getSingle(e).setRepairCost(numNow.intValue() + num.intValue());
		} else if (mode == ChangeMode.REMOVE) {
			inventory.getSingle(e).setRepairCost(numNow.intValue() - num.intValue());
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}