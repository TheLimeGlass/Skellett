package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] slime size of %entity%", "[skellett] %entity%'s slime size"})
@Config("SlimeSize")
@PropertyType("COMBINED")
public class ExprSlimeSize extends SimpleExpression<Number>{
	
	private Expression<Entity> entity;
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
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] slime size of %entity%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (entity.getSingle(e) == null) {
			return null;
		}
		if (entity.getSingle(e).toString().equals("CraftSlime") || entity.getSingle(e).toString().equals("CraftMagmaCube")) {
			return new Number[]{((Slime)entity.getSingle(e)).getSize()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (entity.getSingle(e) == null) {
			return;
		}
		Number num = (Number)delta[0];
		Number numNow = ((Slime)entity.getSingle(e)).getSize();
		if (entity.getSingle(e).toString().equals("CraftSlime") || entity.getSingle(e).toString().equals("CraftMagmaCube")) {
			if (mode == ChangeMode.SET) {
				((Slime)entity.getSingle(e)).setSize(num.intValue());
			} else if (mode == ChangeMode.ADD) {
				((Slime)entity.getSingle(e)).setSize(numNow.intValue() + num.intValue());
			} else if (mode == ChangeMode.REMOVE) {
				((Slime)entity.getSingle(e)).setSize(numNow.intValue() - num.intValue());
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}