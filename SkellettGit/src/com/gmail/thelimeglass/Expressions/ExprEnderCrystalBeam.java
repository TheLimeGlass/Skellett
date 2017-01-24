package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
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

@Syntax({"[ender] crystal (target|beam) [location] of %entity%", "%entity%'s [ender] crystal (target|beam) [location]"})
@Config("EnderCrystalBeam")
@PropertyType("COMBINED")
public class ExprEnderCrystalBeam extends SimpleExpression<Location>{
	
	private Expression<Entity> entity;
	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
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
		return "[ender] crystal (target|beam) [location] of %entity%";
	}
	@Override
	@Nullable
	protected Location[] get(Event e) {
		if (entity.getSingle(e) == null) {
			return null;
		}
		if (entity.getSingle(e) instanceof EnderCrystal) {
			return new Location[]{((EnderCrystal)entity.getSingle(e)).getBeamTarget()};
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
				((EnderCrystal)entity.getSingle(e)).setBeamTarget((Location)delta[0]);
			} else {
				((EnderCrystal)entity.getSingle(e)).setBeamTarget(null);
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.DELETE || mode == ChangeMode.REMOVE || mode == ChangeMode.REMOVE_ALL || mode == ChangeMode.RESET) {
			return CollectionUtils.array(Location.class);
		}
		return null;
	}
}