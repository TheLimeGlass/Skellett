package com.gmail.thelimeglass.ReflectionSyntax;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.ReflectionUtil;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("data watcher type (of|from) %*object%")
@Config("DataWatcher")
@PropertyType(ExpressionType.COMBINED)
public class ExprDataWatcherType extends SimpleExpression<Number>{
	
	private Object value;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		value = e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "data watcher type (of|from) %*object%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (Bukkit.getServer().getVersion().contains("MC: 1.7") || Bukkit.getServer().getVersion().contains("MC: 1.8")) {
			Class<?> ItemStack = null;
			Class<?> ChunkCoordinates = null;
			Class<?> BlockPosition = null;
			Class<?> Vector3f = null;
			try {
				ItemStack = ReflectionUtil.getNMSClass("IChatBaseComponent");
				ChunkCoordinates = ReflectionUtil.getNMSClass("ChunkCoordinates");
				BlockPosition = ReflectionUtil.getNMSClass("BlockPosition");
				Vector3f = ReflectionUtil.getNMSClass("Vector3f");
			} catch (ClassNotFoundException e1) {}
			int type = 0;
			if (value instanceof Number) {
				if (value instanceof Byte) {
					type = 0;
				} else if (value instanceof Short) {
					type = 1;
				} else if (value instanceof Integer) {
					type = 2;
				} else if (value instanceof Float) {
					type = 3;
				}
			} else if (value instanceof String) {
				type = 4;
			} else if (value != null && value.getClass().equals(ItemStack)) {
				type = 5;
			} else if (value != null && (value.getClass().equals(ChunkCoordinates) || value.getClass().equals(BlockPosition))) {
				type = 6;
			} else if (value != null && value.getClass().equals(Vector3f)) {
				type = 7;
			}
			return new Number[]{type};
		} else {
			int type = 0;
			if (value instanceof Number) {
				if (value instanceof Byte) {
					type = 0;
				} else if (value instanceof Float) {
					type = 2;
				}
			} else if (value instanceof String) {
				type = 3;
			} else if (value instanceof Boolean) {
				type = 6;
			}
			return new Number[]{type};
		}
	}
}