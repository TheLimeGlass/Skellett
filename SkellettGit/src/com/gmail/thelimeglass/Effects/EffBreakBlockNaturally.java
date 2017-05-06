package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax({"[skellett] break %block% [naturally] [(with|using) %-itemstack%]", "[skellett] [naturally] break %block% [(with|using) %-itemstack%]"})
@Config("BreakBlockNaturally")
public class EffBreakBlockNaturally extends Effect {
	
	private Expression<Block> block;
	private Expression<ItemStack> item;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		block = (Expression<Block>) e[0];
		item = (Expression<ItemStack>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] break %block% [naturally] [(with|using) %-itemstack%]";
	}
	@Override
	protected void execute(Event e) {
		if (item != null) {
			block.getSingle(e).breakNaturally(item.getSingle(e));
		} else {
			block.getSingle(e).breakNaturally();
		}
	}
}