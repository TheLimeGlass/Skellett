package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprBreedingItem extends SimpleExpression<ItemStack>{
	
	//bre[e]d[ing] (item|material) [used]
	
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(EntityBreedEvent.class)) {
			Skript.error("You can not use Breed item expression in any event but on breeding event!");
			return false;
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "breed[ing] (item|material) [used]";
	}
	@Override
	@Nullable
	protected ItemStack[] get(Event e) {
		return new ItemStack[]{((EntityBreedEvent)e).getBredWith()};
	}
}