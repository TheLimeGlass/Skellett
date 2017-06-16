package com.gmail.thelimeglass.Expressions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.ReflectionUtil;
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

@Syntax({"[the] block (break delay|durability|hardness) of [all] %block%", "[all] %block%'s block (break delay|durability|hardness)"})
@Config("BlockHardness")
@PropertyType(ExpressionType.COMBINED)
public class ExprBlockHardness extends SimpleExpression<Number>{
	
	private Expression<Block> block;
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
		block = (Expression<Block>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] block (break delay|durability|hardness) of [all] %block%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (block != null) {
			return new Number[]{getHardness(block.getSingle(e))};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number strength = (Number)delta[0];
		if (block != null) {
			try {
				Number strengthNow = getHardness(block.getSingle(e));
				Method method = ReflectionUtil.getNMSClass("Block").getDeclaredMethod("c", Float.TYPE);
				method.setAccessible(true);
				Object nmsBlock = ReflectionUtil.getNMSBlock(block.getSingle(e));
				if (mode == ChangeMode.SET) {
					method.invoke(nmsBlock, strength.floatValue());
				} else if (mode == ChangeMode.ADD) {
					method.invoke(nmsBlock, strength.floatValue() + strengthNow.floatValue());
				} else if (mode == ChangeMode.REMOVE) {
					method.invoke(nmsBlock, strength.floatValue() - strengthNow.floatValue());
				}
			} catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
	
	private static float getHardness(Block block) {
		try {
			Object nmsBlock = ReflectionUtil.getNMSBlock(block);
			Field field = ReflectionUtil.getNMSClass("Block").getDeclaredField("strength");
			field.setAccessible(true);
			return field.getFloat(nmsBlock);
		} catch (SecurityException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e1) {
			e1.printStackTrace();
		}
		return 0F;
	}
}