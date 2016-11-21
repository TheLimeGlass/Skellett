package com.gmail.thelimeglass.versionControl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprSilentState extends SimpleExpression<Boolean>{
	
	//(silent|quiet) [state] [of] [entit(y|ies)] %entitys%
	
	private Expression<Entity> entities;
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entities = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "(silent|quiet) [state] [of] [entit(y|ies)] %entitys%";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		List<Boolean> silentStates = new ArrayList<Boolean>();
		for (final Entity ent : entities.getAll(e)) {
			if (ent != null) {
				net.minecraft.server.v1_10_R1.Entity nmsEnt = ((CraftEntity)ent).getHandle();
				silentStates.add((Boolean)nmsEnt.isSilent());
			}
		}
		return silentStates.toArray(new Boolean[silentStates.size()]);
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET)
			for (final Entity ent : entities.getAll(e)) {
				if (ent != null) {
					net.minecraft.server.v1_10_R1.Entity nmsEnt = ((CraftEntity)ent).getHandle();
					nmsEnt.setSilent((Boolean)delta[0]);
				}
			}
		if (mode == ChangeMode.RESET || mode == ChangeMode.REMOVE_ALL)
			for (final Entity ent : entities.getAll(e)) {
				if (ent != null) {
					net.minecraft.server.v1_10_R1.Entity nmsEnt = ((CraftEntity)ent).getHandle();
					nmsEnt.setSilent(false);
				}
			}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.REMOVE_ALL)
			return CollectionUtils.array(Boolean.class);
		return null;
	}
}