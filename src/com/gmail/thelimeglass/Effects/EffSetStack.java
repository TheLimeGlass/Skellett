package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class EffSetStack extends Effect {

	//[skellett] [set] (size|number|amount) of %itemstack% to %number%
	
	private Expression<ItemStack> item;
	private Expression<Number> number;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		item = (Expression<ItemStack>) e[0];
		number = (Expression<Number>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] [set] (size|number|amount) of %itemstack% to %number%";
	}
	@Override
	protected void execute(Event e) {
		if (item.getSingle(e) != null) {
			((ItemStack)item.getSingle(e)).setAmount(((Number)number.getSingle(e)).intValue());
		}
	}
}
