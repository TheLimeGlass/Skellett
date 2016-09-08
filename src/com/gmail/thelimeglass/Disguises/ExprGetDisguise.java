package com.gmail.thelimeglass.Disguises;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
	
public class ExprGetDisguise extends SimpleExpression<DisguiseType> {
	
	//[skellett] [[Libs]Disguises] Disguise of %entities%[[']s]
	//[skellett] [[Libs]Disguises] %entities%'s disguise
	
	private Expression<Entity> entity;
	public Class<? extends DisguiseType> getReturnType() {
		return DisguiseType.class;
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
		return "Disguise of %entitys%";
	}
	@Override
	@Nullable
	protected DisguiseType[] get(Event e) {
		if (DisguiseAPI.getDisguise(entity.getSingle(e)) != null) {
			return new DisguiseType[]{DisguiseAPI.getDisguise(entity.getSingle(e)).getType()};
		} else {
			return null;
		}
	}
}