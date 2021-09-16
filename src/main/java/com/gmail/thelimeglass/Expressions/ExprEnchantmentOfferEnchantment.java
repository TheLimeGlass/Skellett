package com.gmail.thelimeglass.Expressions;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.gmail.thelimeglass.Utils.Annotations.Version;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[the] enchant[ment] of [enchant[ment]] offer %enchantmentoffer%", "offer %enchantmentoffer%'s [enchant[ment]] enchant[ment]"})
@Config("Main.PrepareEnchant")
@FullConfig
@Version("1.11.2")
@PropertyType(ExpressionType.COMBINED)
public class ExprEnchantmentOfferEnchantment extends SimpleExpression<Enchantment>{
	
	private Expression<EnchantmentOffer> offer;
	@Override
	public Class<? extends Enchantment> getReturnType() {
		return Enchantment.class;
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
		return "[the] enchant[ment] of [enchant[ment]] offer %enchantmentoffer%";
	}
	@Override
	@Nullable
	protected Enchantment[] get(Event e) {
		if (offer.getSingle(e) == null) {
			return null;
		}
		return new Enchantment[]{offer.getSingle(e).getEnchantment()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (offer.getSingle(e) == null) {
				return;
			}
			offer.getSingle(e).setEnchantment((Enchantment)delta[0]);
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Enchantment.class);
		}
		return null;
	}
}