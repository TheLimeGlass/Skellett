package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Llama;
import org.bukkit.event.Event;
import org.bukkit.inventory.LlamaInventory;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterEnum;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.Utils.Version;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"inventory of Llama %entity%", "Llama inventory of %entity%"})
@Config("LlamaInventory")
@PropertyType(ExpressionType.COMBINED)
@Version("1.11")
@RegisterEnum("llamainventory")
public class ExprLlamaInventory extends SimpleExpression<LlamaInventory>{
	
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