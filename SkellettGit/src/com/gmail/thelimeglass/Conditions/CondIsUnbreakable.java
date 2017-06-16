package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.gmail.thelimeglass.Utils.Annotations.Version;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("%itemstack% (1¦is|2¦is(n't| not)) unbreakable")
@Config("Syntax.Expressions.Unbreakable")
@FullConfig
@Version("1.11.2")
public class CondIsUnbreakable extends Condition {
	
	private Expression<ItemStack> item;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		item = (Expression<ItemStack>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "%itemstack% (1¦is|2¦is(n't| not)) unbreakable";
	}
	public boolean check(Event e) {
		if (item.getSingle(e).getItemMeta().isUnbreakable()) {
			return isNegated();
		} else {
			return !isNegated();
		}
	}
}