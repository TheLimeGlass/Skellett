package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("[set] collid(e|able) [state] [of] %entity% to %boolean%")
@Config("CollidableState")
public class EffSetCollidable extends Effect {
	
	private Expression<LivingEntity> entity;
	private Expression<Boolean> boo;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<LivingEntity>) e[0];
		boo = (Expression<Boolean>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[set] collid(e|able) [state] [of] %entity% to %boolean%";
	}
	@Override
	protected void execute(Event e) {
		((LivingEntity)entity.getSingle(e)).setCollidable(boo.getSingle(e));
	}
}
