package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprGlowingSpectralArrow extends SimpleExpression<Number>{
	
	//[spectral] arrow glowing time of %entity%
	//%entity%'s [spectral] arrow glowing time
	
	private Expression<Entity> arrow;
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
		arrow = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[spectral] arrow glowing time of %entity%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (e instanceof SpectralArrow) {
			return new Number[]{((SpectralArrow)arrow.getSingle(e)).getGlowingTicks()};
		} else {
			return new Number[]{0};
		}
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (e instanceof SpectralArrow) {
			Number data = (Number)delta[0];
			if (mode == ChangeMode.SET) {
				((SpectralArrow)arrow.getSingle(e)).setGlowingTicks(data.intValue());
			} else if (mode == ChangeMode.ADD) {
				((SpectralArrow)arrow.getSingle(e)).setGlowingTicks(((SpectralArrow)arrow.getSingle(e)).getGlowingTicks() + data.intValue());
			} else if (mode == ChangeMode.REMOVE) {
				((SpectralArrow)arrow.getSingle(e)).setGlowingTicks(((SpectralArrow)arrow.getSingle(e)).getGlowingTicks() - data.intValue());
			}
		}
		return;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(Number.class);
		return null;
	}
}