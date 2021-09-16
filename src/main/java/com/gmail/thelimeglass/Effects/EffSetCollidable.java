package com.gmail.thelimeglass.Effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.gmail.thelimeglass.Utils.Annotations.Version;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[set] collid(e|able) [state] [of] %livingentity% to %boolean%")
@Config("CollidableState")
@Version("1.9")
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
		return "[set] collid(e|able) [state] [of] %livingentity% to %boolean%";
	}
	@Override
	protected void execute(Event e) {
		((LivingEntity)entity.getSingle(e)).setCollidable(boo.getSingle(e));
	}
}
