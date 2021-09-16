package com.gmail.thelimeglass.ArmorStands;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"armo[u]r stand leg[ging][s] of %entity%", "armo[u]r stand %entity%'s leg[ging][s]"})
@Config("Main.ArmorStands")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprArmorStandItemLeggings extends SimpleExpression<ItemStack>{
	
	private Expression<Entity> entity;
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
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "armo[u]r stand leg[ging][s] of %entity%";
	}
	@Override
	@Nullable
	protected ItemStack[] get(Event e) {
		if (entity.getSingle(e) == null) {
			return null;
		}
		if (entity.getSingle(e) instanceof ArmorStand) {
			return new ItemStack[]{((ArmorStand)entity.getSingle(e)).getLeggings()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (entity.getSingle(e) == null) {
			return;
		}
		if (entity.getSingle(e) instanceof ArmorStand) {
			if (mode == ChangeMode.SET) {
				((ArmorStand)entity.getSingle(e)).setLeggings((ItemStack)delta[0]);
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(ItemStack.class);
		}
		return null;
	}
}