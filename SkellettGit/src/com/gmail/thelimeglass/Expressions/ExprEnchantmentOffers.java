package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.event.Event;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterSimpleEnum;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.Utils.Version;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(the|all)] [of] [the] enchant[ment] offers")
@Config("Main.PrepareEnchant")
@FullConfig
@Version("1.11.2")
@PropertyType(ExpressionType.SIMPLE)
@RegisterSimpleEnum(ExprClass=EnchantmentOffer.class, value="enchantmentoffer")
public class ExprEnchantmentOffers extends SimpleExpression<EnchantmentOffer> {
	
	public Class<? extends EnchantmentOffer> getReturnType() {
		return EnchantmentOffer.class;
	}
	public boolean isSingle() {
		return false;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(PrepareItemEnchantEvent.class)) {
			Skript.error("You can not use enchantment offers expression in any event but on enchant prepare!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "enchantment offers";
	}
	@Nullable
	protected EnchantmentOffer[] get(Event e) {
		if (((PrepareItemEnchantEvent)e).getOffers() != null) {
			return ((PrepareItemEnchantEvent)e).getOffers();
		}
		return null;
	}
}