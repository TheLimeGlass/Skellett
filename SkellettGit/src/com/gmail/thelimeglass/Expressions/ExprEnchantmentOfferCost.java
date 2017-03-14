package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.Utils.Version;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[the] cost of [enchant[ment]] offer %enchantmentoffer%", "offer %enchantmentoffer%'s [enchant[ment]] cost"})
@Config("Main.PrepareEnchant")
@FullConfig
@Version("1.11.2")
@PropertyType(ExpressionType.COMBINED)
public class ExprEnchantmentOfferCost extends SimpleExpression<Number>{
	
	private Expression<EnchantmentOffer> offer;
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
		offer = (Expression<EnchantmentOffer>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] cost of [enchant[ment]] offer %enchantmentoffer%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{offer.getSingle(e).getCost()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number num = (Number)delta[0];
		Number numNow = offer.getSingle(e).getCost();
		if (mode == ChangeMode.SET) {
			offer.getSingle(e).setCost(num.intValue());
		} else if (mode == ChangeMode.RESET) {
			offer.getSingle(e).setCost(0);
		} else if (mode == ChangeMode.ADD) {
			offer.getSingle(e).setCost(numNow.intValue() + num.intValue());
		} else if (mode == ChangeMode.REMOVE) {
			offer.getSingle(e).setCost(numNow.intValue() - num.intValue());
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