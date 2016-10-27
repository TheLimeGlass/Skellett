package com.gmail.thelimeglass.Disguises;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.libraryaddict.disguise.DisguiseAPI;
	
public class ExprSelfViewDisguise extends SimpleExpression<Boolean> {
	
	//[skellett] [[Libs]Disguises] self view[ing] disguise [state] of %entities%[[']s]
	//[skellett] [[Libs]Disguises] %entities%'s self view[ing] disguise [state]
	
	private Expression<Entity> entity;
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
		entity = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "self view[ing] disguise [state] of %entities%[[']s]";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		if (DisguiseAPI.getDisguise(entity.getSingle(e)) != null) {
			return new Boolean[]{DisguiseAPI.isViewSelfToggled(entity.getSingle(e))};
		} else {
			return null;
		}
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			DisguiseAPI.setViewDisguiseToggled(entity.getSingle(e), (Boolean)delta[0]);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(Boolean.class);
		return null;
	}
}