package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[ender] crystal base [visib(le|ility)] [state] of %entity%", "%entity%'s [ender] crystal base [visib(le|ility)] [state]"})
@Config("EnderCrystalBase")
@PropertyType(ExpressionType.SIMPLE)
public class ExprEnderCrystalBase extends SimpleExpression<Boolean>{
	
	private Expression<Entity> entity;
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
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
		return "%entity%'s [ender] crystal base [visib(le|ility)] [state]";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		if (entity.getSingle(e) == null) {
			return null;
		}
		if (entity.getSingle(e) instanceof EnderCrystal) {
			return new Boolean[]{((EnderCrystal)entity.getSingle(e)).isShowingBottom()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (entity.getSingle(e) == null) {
			return;
		}
		if (entity.getSingle(e) instanceof EnderCrystal) {
			if (mode == ChangeMode.SET) {
				((EnderCrystal)entity.getSingle(e)).setShowingBottom((Boolean)delta[0]);
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Boolean.class);
		}
		return null;
	}
}