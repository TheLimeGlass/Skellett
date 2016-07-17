package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Main;

public class EffCustomName extends Effect {

	//[skellett] [set] custom[ ]name of %entity% to %string%
	
	private Expression<LivingEntity> entity;
	private Expression<String> tag;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<LivingEntity>) e[0];
		tag = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] [set] custom[ ]name of %entity% to %string%";
	}
	@Override
	protected void execute(Event e) {
		((LivingEntity)entity.getSingle(e)).setCustomName(Main.cc(tag.getSingle(e)));
		((LivingEntity)entity.getSingle(e)).setCustomNameVisible(true);
	}
}
