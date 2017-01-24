package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"(fuel|burning) [item] (of|in) [brew[ing] stand] [inventory] %brewerinventory%", "[brew[ing] stand] %brewerinventory%'s (fuel|burning) [item]"})
@Config("Main.Brewing")
@FullConfig
@PropertyType("COMBINED")
public class ExprBrewingInventoryFuel extends SimpleExpression<ItemStack>{
	
	private Expression<BrewerInventory> inventory;
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
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
		return "(fuel|burning) [item] (of|in) [brew[ing] stand] [inventory] %brewerinventory%";
	}
	@Override
	@Nullable
	protected ItemStack[] get(Event e) {
		if (inventory == null) {
			return null;
		}
		return new ItemStack[]{inventory.getSingle(e).getFuel()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (inventory == null) {
			return;
		}
		if (mode == ChangeMode.SET) {
			inventory.getSingle(e).setFuel((ItemStack)delta[0]);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(ItemStack.class);
		}
		return null;
	}
}