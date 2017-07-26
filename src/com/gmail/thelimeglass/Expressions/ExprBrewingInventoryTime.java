package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.BrewerInventory;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
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

@Syntax({"[brew[ing]] time (of|in) brew[ing] [stand] [inventory] %brewerinventory%", "brew[ing] [stand] %brewerinventory%'s [brew[ing]] time"})
@Config("Main.Brewing")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprBrewingInventoryTime extends SimpleExpression<Number>{
	
	private Expression<BrewerInventory> inventory;
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
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		inventory = (Expression<BrewerInventory>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[brew[ing]] time (of|in) [brew[ing] stand] [inventory] %brewerinventory%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (inventory == null) {
			return null;
		}
		return new Number[]{inventory.getSingle(e).getHolder().getBrewingTime()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (inventory == null) {
			return;
		}
		Number num = (Number)delta[0];
		Number numNow = inventory.getSingle(e).getHolder().getBrewingTime();
		if (mode == ChangeMode.SET) {
			inventory.getSingle(e).getHolder().setBrewingTime(num.intValue());
		} else if (mode == ChangeMode.RESET) {
			inventory.getSingle(e).getHolder().setBrewingTime(0);
		} else if (mode == ChangeMode.ADD) {
			inventory.getSingle(e).getHolder().setBrewingTime(numNow.intValue() + num.intValue());
		} else if (mode == ChangeMode.REMOVE) {
			inventory.getSingle(e).getHolder().setBrewingTime(numNow.intValue() - num.intValue());
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