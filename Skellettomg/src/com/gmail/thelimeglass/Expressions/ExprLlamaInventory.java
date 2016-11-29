package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Llama;
import org.bukkit.event.Event;
import org.bukkit.inventory.LlamaInventory;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprLlamaInventory extends SimpleExpression<LlamaInventory>{
	
	//inventory of Llama %entity%
	//Llama inventory of %entity%
	
	private Expression<Entity> llama;
	@Override
	public Class<? extends LlamaInventory> getReturnType() {
		return LlamaInventory.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		llama = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "inventory of Llama %entity%";
	}
	@Override
	@Nullable
	protected LlamaInventory[] get(Event e) {
		if (llama.getSingle(e) instanceof Llama) {
			return new LlamaInventory[]{((Llama) llama.getSingle(e)).getInventory()};
		}
		return null;
	}
}