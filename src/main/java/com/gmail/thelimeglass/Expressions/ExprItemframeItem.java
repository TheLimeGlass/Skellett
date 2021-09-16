package com.gmail.thelimeglass.Expressions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
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

@Syntax({"[skellett] item (in|inside|within|of|from) item[ ]frame %entity%", "[skellett] %entity%'s item[ ]frame item", "[skellett] item[ ]frame %entity%'s item"})
@Config("ItemframeItem")
@PropertyType(ExpressionType.COMBINED)
public class ExprItemframeItem extends SimpleExpression<ItemStack>{
	
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
		return "item (in|inside|of|from) item[ ]frame %entity%";
	}
	@Override
	@Nullable
	protected ItemStack[] get(Event e) {
		if (entity != null) {
			if (entity.getSingle(e) instanceof ItemFrame) {
				return new ItemStack[]{((ItemFrame)entity.getSingle(e)).getItem()};
			}
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (entity != null) {
				if (entity.getSingle(e) instanceof ItemFrame) {
					((ItemFrame)entity.getSingle(e)).setItem((ItemStack)delta[0]);
				}
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